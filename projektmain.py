from datetime import datetime, timedelta
from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
import os
import copy

from flask_bcrypt import Bcrypt

from flask_jwt_extended import (JWTManager, jwt_required, create_access_token, get_jwt_identity, get_raw_jwt)

app = Flask(__name__)
db_uri = 'sqlite:///projektdb.db'

if 'DATABASE_URL' in os.environ:
    db_uri = os.environ['DATABASE_URL']
else:  # when running locally: use sqlite
    db_path = os.path.join(os.path.dirname(__file__), 'projektdb.db')
    db_uri = 'sqlite:///{}'.format(db_path)
    debug_flag = True

app.config.from_object("config.Config")

app.config['SQLALCHEMY_DATABASE_URI'] = db_uri
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

app.config['JWT_BLACKLIST_ENABLED'] = True
app.config['JWT_BLACKLIST_TOKEN_CHECKS'] = ['access']
app.config['JWT_SECRET_KEY'] = app.config['SERVER_SECRET']
app.config['JWT_ACCESS_TOKEN_EXPIRES'] = timedelta(minutes=500)

db = SQLAlchemy(app)
bcrypt = Bcrypt(app)
jwt = JWTManager(app)
blacklist = set()

users_created = db.Table('users_created',
                         db.Column('user_id', db.Integer, db.ForeignKey('User.user_id', ondelete="CASCADE"),
                                   primary_key=True),
                         db.Column('message_created_by', db.Integer, db.ForeignKey('Post.post_id', ondelete="CASCADE"),
                                   primary_key=True)
                         )
users_liked = db.Table('users_liked',
                       db.Column('user_id', db.Integer, db.ForeignKey('User.user_id', ondelete="CASCADE"),
                                 primary_key=True),
                       db.Column('message_liked', db.Integer, db.ForeignKey('Post.post_id', ondelete="CASCADE"),
                                 primary_key=True)
                       )
users_followed = db.Table('users_followed',
                          db.Column('following_id', db.Integer, db.ForeignKey('User.user_id', ondelete="CASCADE"),
                                    primary_key=True),
                          db.Column('followed_id', db.Integer, db.ForeignKey('User.user_id', ondelete="CASCADE"),
                                    primary_key=True)
                          )


class User(db.Model):
    __tablename__ = "User"

    user_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    user_name = db.Column(db.String(255), unique=False, nullable=False)
    user_password = db.Column(db.String(255), unique=False, nullable=False)
    user_email = db.Column(db.String(255), unique=True, nullable=False)

    post_created = db.relationship('Post', secondary=users_created, backref="post_created")
    post_liked = db.relationship('Post', secondary=users_liked, backref=db.backref("post_liked"))
    user_following = db.relationship('User', secondary=users_followed,
                                     primaryjoin=user_id == users_followed.c.following_id,
                                     secondaryjoin=user_id == users_followed.c.followed_id)

    def __init__(self, name, password, email):
        self.user_name = name
        self.user_password = bcrypt.generate_password_hash(password).decode('utf-8')
        self.user_email = email

    def to_dict(self):
        return {'id': self.user_id, 'name': self.user_name, 'password': self.user_password,
                'email': self.user_email, 'created_posts': [x.to_dict() for x in self.post_created],
                'liked_posts': [x.to_dict() for x in self.post_liked],
                'user_following': [x.user_id for x in self.user_following]}


class Post(db.Model):
    __tablename__ = "Post"
    post_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    post_title = db.Column(db.String(255), unique=False, nullable=False)
    post_price = db.Column(db.String(255), unique=False, nullable=False)
    post_desc = db.Column(db.String(255), unique=False, nullable=False)

    _commented_by = db.Column(db.String, default="")
    _comments = db.Column(db.String, default="")

    def __init__(self, post_title, post_price, post_desc):
        self.post_title = post_title
        self.post_price = post_price
        self.post_desc = post_desc

    @property
    def comments(self):
        return [str(x) for x in self._comments.split(';')]

    @comments.setter
    def comments(self, comment):
        if self._comments is None:
            self._comments = comment
        else:
            self._comments += '%s;' % comment

    @property
    def commented_by(self):
        return [str(x) for x in self._commented_by.split(';')]

    @commented_by.setter
    def commented_by(self, user_email):
        if self._commented_by is None:
            self._commented_by = user_email
        else:
            self._commented_by += '%s;' % user_email

    def to_dict(self):
        return {'id': self.post_id, 'title': self.post_title, 'price': self.post_price, 'desc': self.post_desc,
                'comments': [x for x in self.comments], 'commentedby': [x for x in self.commented_by]}


@jwt.token_in_blacklist_loader
def check_if_token_in_blacklist(decrypted_token):
    jti = decrypted_token['jti']
    return jti in blacklist


@app.route('/user/register/<username>/<password>/<email>', methods=['POST'])
def register_user(username, password, email):
    existing_user = User.query.filter_by(user_email=email).first()
    if existing_user is None:
        new_user = User(username, password, email)
        db.session.add(new_user)
        db.session.commit()
        return jsonify(message='Användare Registerad'), 200
    return jsonify(message='Email readan tagen'), 400


@app.route('/user/login/<email>/<password>', methods=['POST'])
def login_user(email, password):
    existing_user = User.query.filter_by(user_email=email).first()

    if request.method == 'POST':
        if existing_user is None:
            return jsonify(Error="Ingen sådan användare"), 400
        if bcrypt.check_password_hash(existing_user.user_password, password):
            user_token = create_access_token(identity=existing_user.user_email)
            return jsonify(access_token=user_token), 200
        return jsonify(Error="Fel Lösenord"), 400


@app.route('/logout', methods=['DELETE'])
@jwt_required
def logout():
    jti = get_raw_jwt()['jti']
    blacklist.add(jti)
    return jsonify(msg="Du är utloggad"), 200


@app.route('/user/createpost/<title>/<price>/<description>', methods=['POST'])
@jwt_required
def create_post(title, price, description):
    new_post = Post(title, price, description)
    db.session.add(new_post)
    logged_in_user = get_curr_user()
    if logged_in_user is None:
        return jsonify(Error="Ingen sådan användare"), 400
    else:
        logged_in_user.post_created.append(new_post)
    db.session.commit()
    return jsonify(message='Inlägg Skapat'), 200


@app.route('/post/getcreator/<id_post>', methods=['POST'])
def get_creator(id_post):
    user_creator_id = User.query.filter(User.post_created.any(post_id=id_post)).first().user_id
    if user_creator_id is None:
        return jsonify(Error='Inget inlägg hittat'), 400
    else:
        return jsonify(creator_id=user_creator_id)


@app.route('/user/likepost/<id_post>', methods=['POST'])
@jwt_required
def like_post(id_post):
    searched_post = Post.query.filter_by(post_id=id_post).first()
    logged_in_user = get_curr_user()
    if searched_post is None:
        return jsonify(Error='Inget inlägg hittat'), 400

    elif searched_post in logged_in_user.post_liked:
        return jsonify(Message=''), 200
    elif searched_post in logged_in_user.post_created:
        return jsonify(Error="Kan inte gilla sitt eget inlägg"), 400
    else:
        logged_in_user.post_liked.append(searched_post)

    db.session.commit()
    return jsonify(Message = "Inlägg Gillat"),200


@app.route('/user/unlikepost/<id_post>', methods=['POST'])
@jwt_required
def unlike_post(id_post):
    searched_post = Post.query.filter_by(post_id=id_post).first()
    logged_in_user = get_curr_user()
    if searched_post is None:
        return jsonify(Error='Inget inlägg hittat'), 400
    else:
        if searched_post not in logged_in_user.post_liked:
            return jsonify(Error="Kan inte ogillat inte gillat inlägg"), 400
        else:
            logged_in_user.post_liked.remove(searched_post)

    db.session.commit()
    return jsonify(Message = "Inlägg Ogillat"),200


@app.route('/user/followuser/<id_user>', methods=['POST'])
@jwt_required
def follow_user(id_user):
    searched_user = User.query.filter_by(user_id=id_user).first()
    logged_in_user = get_curr_user()
    if searched_user is None:
        return jsonify(Error='Inget inlägg hittat'), 400
    elif searched_user in logged_in_user.user_following or searched_user == logged_in_user:
        return jsonify(Message=''), 200
    else:
        logged_in_user.user_following.append(searched_user)
        db.session.commit()
        return jsonify(Message='Användare följd'), 200


@app.route('/user/unfollowuser/<id_user>', methods=['POST'])
@jwt_required
def unfollow_user(id_user):
    searched_user = User.query.filter_by(user_id=id_user).first()
    logged_in_user = get_curr_user()
    if searched_user is None:
        return jsonify(Error='Ingen användare hittad'), 400
    elif searched_user not in logged_in_user.user_following:
        return jsonify(Error="Kan inte ofölja en användare som du inte följer"), 400
    else:
        logged_in_user.user_following.remove(searched_user)
        db.session.commit()
        return jsonify(Message="Du följer inte längre användaren"), 200


@app.route('/user/deletepost/<id_post>', methods=['DELETE'])
@jwt_required
def delete_post(id_post):
    searched_post = Post.query.filter_by(post_id=id_post).first()
    logged_in_user = get_current_user()
    if searched_post not in logged_in_user.post_created:
        return jsonify(Error="Inget inlägg hittat"), 400
    else:
        db.session.delete(searched_post)
        db.session.commit()
        return jsonify(Message="Inlägg Borttaget"), 400


@app.route('/user/comment/<id_post>/<comment>', methods=['POST'])
@jwt_required
def add_comment(id_post, comment):
    searched_post = Post.query.filter_by(post_id=id_post).first()
    logged_in_user = get_curr_user()
    if not searched_post:
        return jsonify(Error="Inget inlägg hittat"), 400
    elif searched_post in logged_in_user.post_created:
        return jsonify(Error="Kan inte kommentera eget inlägg"), 400
    else:
        searched_post.comments = comment
        searched_post.commented_by = logged_in_user.user_email
        db.session.commit()
        return jsonify(Message="Inlägg kommenterat"), 200


@app.route('/user/all')
@jwt_required
def get_all_users():
    all_users = User.query.all()
    result = {'all': [x.to_dict() for x in all_users]}
    return jsonify(result)


@app.route('/post/all', methods=['POST'])
def get_all_posts():
    all_posts = Post.query.all()
    if all_posts is None:
        return jsonify(Error='Hittad inga inlägg'), 400
    else:
        result = {'all': [x.to_dict() for x in all_posts]}
        return jsonify(result)


@app.route('/user/get/<email>', methods=['POST'])
def get_user(email):
    user_search = User.query.filter_by(user_email=email).first()
    if user_search is None:
        return jsonify(Error='Ingen sådan användare'), 400
    else:
        return jsonify(user_search.to_dict()), 200


@app.route('/user/current', methods=['POST'])
@jwt_required
def get_current_user():
    curr_user = get_jwt_identity()
    current_user = User.query.filter_by(user_email=curr_user).first()
    return jsonify(current_user.to_dict())


@app.route('/user/getfollowedpost', methods=['POST'])
@jwt_required
def get_followed_posts():
    current_user_following = get_curr_user().user_following
    followed_user_posts = []
    if len(current_user_following) == 0:
        return jsonify(followed_posts=[]), 400
    else:
        for user_followed in current_user_following:
            follow_user = User.query.filter_by(user_id=user_followed.user_id).first()
            followed_user_posts.append(follow_user.post_created)
        result = {'followed_posts': [x.to_dict() for x in followed_user_posts[0]]}
        return result, 200


def get_curr_user():
    curr_user = get_jwt_identity()
    current_user = User.query.filter_by(user_email=curr_user).first()
    return current_user


def init_db():
    db.drop_all()
    db.create_all()


@app.route('/', methods=['GET'])
def start_page():
    return 'liubiljett', 200


if __name__ == "__main__":
    db.drop_all()
    db.create_all()
    app.run(port=5000, debug=True)
