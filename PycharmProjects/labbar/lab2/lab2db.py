from flask_sqlalchemy import SQLAlchemy
import uuid
from lab2server import app

app.config['SQLALCHEMY_DATABASE_URI'] = "sqlite:///dblab2.db"
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = True

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
    # .filter(user_id == users_messages).all()
    out = []
    result = db.session.query(User).join(users_messages).filter(users_messages != user_id)
    # print(result)
    for elem in result:
        # print(elem)
        # if elem.id != user_id:
        out.append(elem)
    return {'res': x.to_dict() for x in out}


# init_db()
# uid = "test"
# del_message("test")
uid1 = save_message('test')['id']
uid2 = save_message('test')['id']
print(uid1)
mark_read(uid1, 1)
mark_read(uid2, 1)

# print(get_message(uid['id']))
print(get_unread(1))
print('test')
