from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
import os

from flask_bcrypt import Bcrypt

from flask_jwt_extended import (JWTManager, jwt_required, create_access_token, get_raw_jwt)

app = Flask(__name__)
db_uri = 'sqlite:///projektdb.db'

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
    user_id = db.Column(db.Integer, primary_key=True)
    user_name = db.Column(db.String(255), unique=False, nullable=False)
    user_password = db.Column(db.String(255), unique=False, nullable=False)
    user_email = db.Column(db.String(255), unique=True, nullable=False)
    user_section = db.Column(db.String(25), unique=False, nullable=False)

    def __init__(self, name, password, email, section):
        self.user_name = name
        self.user_password = password
        self.user_email = email
        self.user_section = section

    def to_dict(self):
        return {'id': self.user_id, 'username': self.user_name, 'password': self.user_password,
                'email': self.user_email, 'section': self.user_section}


@jwt.token_in_blacklist_loader
def check_if_token_in_blacklist(decrypted_token):
    jti = decrypted_token['jti']
    return jti in blacklist


@app.route('/user/register/<username>/<password>/<email>/<section>', methods=['POST'])
def register_user(username, password, email, section):
    return "registered"


# if request.method == 'GET':
#     existing_user = User.query.filter_by(user_email=email).first()
#
#     if existing_user is None:
#         new_user = User(username, password, email, section)
#         db.session.add(new_user)
#         db.session.commit()
#         return "{'message':registered}", 200
#     return "{'message':'user email already taken'}", 400


@app.route('/user/login/<email>/<password>', methods=['POST'])
def login_user(email, password):
    existing_user = User.query.filter_by(user_email=email).first()
    if request.method == 'GET':
        if existing_user is None:
            return "{'Error': 'No such user'}", 400
        if bcrypt.check_password_hash(existing_user.user_password, password):
            user_token = create_access_token(identity=existing_user.user_email)
            return jsonify(access_token=user_token), 200
        return "{'Error':'Wrong password'}", 400


@app.route('/user/all')
@jwt_required
def get_all_users():
    all_users = User.query.all()
    result = {'all': [x.to_dict() for x in all_users]}
    return jsonify(result)


@app.route('/')
def start_page():
    return "liublijett"


if __name__ == "__main__":
    app.run(port=5000, debug=True)
