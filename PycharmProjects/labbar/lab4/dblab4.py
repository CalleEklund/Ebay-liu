from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
import os

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


# class Blacklist(db.Model):
#     id = db.Column(db.Integer, primary_key=True)
#     jti = db.Column(db.String(36), nullable=False)
#logout_user
#     def __init__(self, jti):
#         self.jti = jti

@jwt.token_in_blacklist_loader
def check_if_token_in_blacklist(decrypted_token):
    jti = decrypted_token['jti']
    return jti in blacklist

    #return db.session.in_blacklist(jti)


@app.route('/')
def start_page():
    return 'Start Page'


@app.route('/user/register/<username>/<password>',methods=['POST'])
def register(username, password):
    print(username, password)
    if request.method == 'POST':
        user = User.query.filter_by(user_name=username).first()

        if user == None:
            user = User(username, password)
            db.session.add(user)
            db.session.commit()
            return "{'message':registered}", 200
        return "{'message':'username already taken'}", 409


@app.route('/user/login/<username>/<password>',methods=['POST'])
def login(username, password):
    user = User.query.filter_by(user_name=username).first()
    # print('test')

    if request.method == 'POST':
        if user == None:
            return "{'Error':'No such user'}", 400
        if bcrypt.check_password_hash(user.password, password):
            token = create_access_token(identity=user.user_name)
            return jsonify(access_token=token), 200
        return "{'Error':'Wrong password'}", 400


@app.route('/user/all')
def all_users():
    all = User.query.all()
    result = {'all': [x.to_dict() for x in all]}
    return jsonify(result)


@app.route('/user/logout',methods=['POST'])
@jwt_required
def logout():
    if request.method == 'POST':
        jti = get_raw_jwt()['jti']
        blacklist.add(jti)

        # jti = get_raw_jwt()['jti']
        # db.session.revoke_token(jti)
        return jsonify({'msg': 'logged out'}), 200


@app.route('/protected',methods=['POST'])
@jwt_required
def protected():
    if request.method == 'POST':
        return jsonify({'hello': 'world'})


if __name__ == '__main__':
    app.run(port=5000, debug=False)
    db.drop_all()
    db.create_all()
