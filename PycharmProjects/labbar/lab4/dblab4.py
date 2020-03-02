from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
import os
import uuid

from flask_bcrypt import Bcrypt

from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    get_jwt_identity, get_raw_jwt
)

app = Flask(__name__)
db_uri = 'sqlite:///lab4db.db'
if 'DATABASE_URL' in os.environ:
    db_uri = os.environ['DATABASE_URL']
app.config['SQLALCHEMY_DATABASE_URI'] = db_uri
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

app.config['JWT_BLACKLIST_ENABLED'] = True
app.config['JWT_BLACKLIST_TOKEN_CHECKS'] = ['access']
app.config['JWT_SECRET_KEY'] = 'protect with your life'

db = SQLAlchemy(app)
bcrypt = Bcrypt(app)
jwt = JWTManager(app)

blacklist = set()

users_messages = db.Table('users_messages',
                          db.Column('user_id', db.Integer, db.ForeignKey('User.id'), primary_key=True),
                          db.Column('message_readBy', db.Integer, db.ForeignKey('Message.id'), primary_key=True)
                          )


class User(db.Model):
    __tablename__ = "User"
    id = db.Column(db.Integer, primary_key=True)
    user_name = db.Column(db.String(25), unique=True, nullable=False)
    password = db.Column(db.String(200), unique=False, nullable=False)

    def __init__(self, user, password):
        self.user_name = user
        self.password = bcrypt.generate_password_hash(password).decode('utf-8')

    def to_dict(self):
        return {'id': self.id,
                'username': self.user_name,
                'password': self.password,
                }


class MessageM(db.Model):
    __tablename__ = 'Message'
    id = db.Column(db.String(36), primary_key=True)
    msg = db.Column(db.String(140), nullable=False)
    users = db.relationship('User', secondary=users_messages, backref='messages')

    def __init__(self, p1, p2, p3):
        self.id = p1
        self.msg = p2
        self.users = p3

    def to_dict(self):
        return {'id': self.id,
                'msg': self.msg,
                'users': [x.to_dict() for x in self.users],
                }


# class Blacklist(db.Model):
#     id = db.Column(db.Integer, primary_key=True)
#     jti = db.Column(db.String(36), nullable=False)
# logout_user
#     def __init__(self, jti):
#         self.jti = jti

def store_message(message):
    new_id = str(uuid.uuid4())
    new_message = MessageM(new_id, message, [])
    db.session.add(new_message)
    db.session.commit()
    return new_id


# funkar
def get_msg(message_id):
    msg = MessageM.query.filter_by(id=message_id).first()
    return msg.to_dict()


# funkar, behöver ett Message obj
def del_msg(message_id):
    db.session.query(Message).filter_by(id=message_id).delete()
    db.session.commit()
    return "", 200


# funkar, ger en lista
def get_unread(user_name):
    user = User.query.filter_by(user_name=user_name).first()
    all_msg = MessageM.query.all()
    unread_msg = []
    # print("user",user.to_dict())
    if user is None:
        return "", 404
    else:
        read = MessageM.query.outerjoin(users_messages).filter(user.id == users_messages.c.user_id)

        for message in all_msg:
            if not message in read:
                unread_msg.append(message.to_dict())
    # print(unread_msg)
    return {'res': unread_msg}


# funkar
def mark_read(message_id, username):
    searched_message = MessageM.query.filter_by(id=str(message_id)).first()
    searched_user = User.query.filter_by(user_name=str(username)).first()
    if not searched_message or not searched_user:
        return "", 404
    else:
        searched_message.users.append(searched_user)
    db.session.commit()
    return "", 200


# funkar
def get_all_msg_count():
    all_messages = db.session.query(Message).count()
    return all_messages

def all_messages():
    return [message.to_dict() for message in db.session.query(MessageM).all()]

@jwt.token_in_blacklist_loader
def check_if_token_in_blacklist(decrypted_token):
    jti = decrypted_token['jti']
    return jti in blacklist

    # return db.session.in_blacklist(jti)


@app.route('/')
def start_page():
    return 'Start Page'


@app.route('/user/register/<username>/<password>', methods=['POST'])
def register(username, password):
    if request.method == 'POST':
        user = User.query.filter_by(user_name=username).first()

        if user is None:
            user = User(username, password)
            db.session.add(user)
            db.session.commit()
            return "{'message':registered}", 200
        return "{'message':'username already taken'}", 409


@app.route('/user/login/<username>/<password>', methods=['POST'])
def login(username, password):
    user = User.query.filter_by(user_name=username).first()
    if request.method == 'POST':
        if user is None:
            return "{'Error':'No such user'}", 400
        if bcrypt.check_password_hash(user.password, password):
            token = create_access_token(identity=user.user_name)
            return jsonify(access_token=token), 200
        return "{'Error':'Wrong password'}", 400


@app.route('/user/all')
@jwt_required
def all_users():
    all = User.query.all()
    result = {'all': [x.to_dict() for x in all]}
    return jsonify(result)


@app.route('/user/logout', methods=['POST'])
@jwt_required
def logout():
    if request.method == 'POST':
        jti = get_raw_jwt()['jti']
        blacklist.add(jti)

        # jti = get_raw_jwt()['jti']
        # db.session.revoke_token(jti)
        return jsonify({'msg': 'logged out'}), 200


@app.route('/protected', methods=['POST'])
@jwt_required
def protected():
    if request.method == 'POST':
        return jsonify({'hello': 'world'})


@app.route('/message', methods=['POST'])
@jwt_required
def save_message():
    if request.method == 'POST':
        msg = request.json['message']
        if len(msg) > 140:
            return "", 400
        new_id = store_message(msg)
        outdata = {'id': new_id}
        return jsonify(outdata)


@app.route('/message', methods=['GET'])
def get_all_messages():
    if request.method == 'GET':
        all_msg = all_messages()
        return jsonify(all_msg)


@app.route('/message/<MessageID>', methods=['GET'])
def Message(MessageID):
    if request.method == 'GET':
        msg_obj = get_msg(str(MessageID))
        if msg_obj['id'] is None:
            return "", 404
        return jsonify(msg_obj)


@app.route('/message/<MessageID>', methods=['DELETE'])
@jwt_required
def delete_message(MessageID):
    msg_id = MessageID
    if not msg_id:
        return "", 404
    del_msg(msg_id)
    return "", 200


@app.route('/message/<MessageID>/read/<username>', methods=['GET'])
@jwt_required
def mark_reads(MessageID, username):
    msg_id = MessageID
    if not msg_id or not username:
        return "", 404
    else:
        mark_read(str(msg_id), str(username))
    return "", 200


@app.route('/message/unread/<UserID>', methods=['POST'])
@jwt_required
def get_unreads(UserID):
    user_id = UserID
    output = get_unread(user_id)
    return output


if __name__ == '__main__':
    db.drop_all()
    db.create_all()
    app.run(port=5000, debug=False)
