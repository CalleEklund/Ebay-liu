import os

from flask import jsonify
from flask_sqlalchemy import SQLAlchemy
import uuid
from lab2server import app

if 'NAMESPACE' in os.environ and os.environ['NAMESPACE'] == 'heroku':
    db_uri = os.environ['DATABASE_URL']
    debug_flag = False
else: # when running locally: use sqlite
    db_path = os.path.join(os.path.dirname(__file__), 'dblab2.db')
    db_uri = 'sqlite:///{}'.format(db_path)
    debug_flag = True

db = SQLAlchemy(app)

users_messages = db.Table('users_messages',
                          db.Column('user_id', db.Integer, db.ForeignKey('User.id'), primary_key=True),
                          db.Column('message_readBy', db.Integer, db.ForeignKey('Message.id'), primary_key=True)
                          )


class User(db.Model):
    __tablename__ = 'User'
    id = db.Column(db.Integer, primary_key=True)

    def __init__(self, id):
        self.id = id

    def to_dict(self):
        return {'id': self.id}


class Message(db.Model):
    __tablename__ = 'Message'
    id = db.Column(db.String(36), primary_key=True)
    msg = db.Column(db.String(140), nullable=False)
    users = db.relationship('User', secondary=users_messages, backref='messages')

    def __init__(self, id, msg, users):
        self.id = id
        self.msg = msg
        self.users = users

    def to_dict(self):
        return {'id': self.id,
                'msg': self.msg,
                'users': [x.to_dict() for x in self.users],
                }


def init_db():
    db.drop_all()
    db.create_all()


def all_messages():
    return [message.to_dict() for message in db.session.query(Message).all()]


def save_message(message):
    new_uid = str(uuid.uuid4())
    message = message
    new_message = Message(new_uid, message, [])
    db.session.add(new_message)
    db.session.commit()
    return new_message.to_dict()


def get_message(message_id):
    searched_message = Message.query.filter_by(id=message_id).first()
    return searched_message.to_dict()


def del_message(message_id):
    searched_mesage = Message.query.filter_by(id=message_id).delete()
    if searched_mesage:
        db.session.commit()
        return "", 200
    else:
        return "", 404


def mark_read(message_id, user_id):
    searched_message = Message.query.filter_by(id=str(message_id)).first()
    searched_user = User.query.filter_by(id=int(user_id)).first()
    if not searched_user:
        new_user = User(int(user_id))
        searched_message.users.append(new_user)
    elif not searched_message:
        return "", 404
    else:
        searched_message.users.append(searched_user)
    db.session.commit()
    return "", 200


def get_unread(user_id):
    read = Message.query.outerjoin(users_messages).filter(user_id == users_messages.c.user_id)
    all_msg = Message.query.all()
    unread_msg = []
    if user_id is None:
        return "", 404
    else:
        for message in all_msg:
            if not message in read:
                unread_msg.append(message.to_dict())

    return unread_msg


init_db()

