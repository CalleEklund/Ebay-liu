from app2 import app
from flask_sqlalchemy import SQLAlchemy
import uuid
import os
import json


if 'NAMESPACE' in os.environ and os.environ['NAMESPACE'] == 'heroku':
    db_uri = os.environ['DATABASE_URL']
    debug_flag = False
else: # when running locally: use sqlite
    db_path = os.path.join(os.path.dirname(__file__), 'lab2.db')
    db_uri = 'sqlite:///{}'.format(db_path)
    debug_flag = True

app.config['SQLALCHEMY_DATABASE_URI'] = db_uri
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


class Message(db.Model):
    __tablename__ = 'Message'
    id = db.Column(db.String(36), primary_key=True)
    msg = db.Column(db.String(140), nullable=False)
    users = db.relationship('User', secondary=users_messages, backref='messages')

    def __init__(self, id, msg, users):
        self.id = id
        self.msg = msg
        self.users = users


db.drop_all()
db.create_all()

def initial_insert():
    messages = {'220f7259-beb1-4012-aa59-6e787a0cd581': {'id': '220f7259-beb1-4012-aa59-6e787a0cd581', 'text': 'demo0',
                                                         'readBy': []},
                'a0d84018-d718-4715-a645-ff375d4b3a13': {'id': 'a0d84018-d718-4715-a645-ff375d4b3a13', 'text': 'demo1',
                                                         'readBy': []},
                'afe83630-4cbf-4000-baef-38b13145bf65': {'id': 'afe83630-4cbf-4000-baef-38b13145bf65', 'text': 'demo2',
                                                         'readBy': []},
                '01d6d904-dc32-45fb-9c36-f6c8719cd690': {'id': '01d6d904-dc32-45fb-9c36-f6c8719cd690', 'text': 'demo3',
                                                         'readBy': []},
                'a432a82a-6c4b-4c42-b9e0-6713e52b81cb': {'id': 'a432a82a-6c4b-4c42-b9e0-6713e52b81cb', 'text': 'demo4',
                                                         'readBy': []},
                'ef9c846a-3477-4506-8f5c-a7200d5fab71': {'id': 'ef9c846a-3477-4506-8f5c-a7200d5fab71', 'text': 'demo5',
                                                         'readBy': []},
                '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4': {'id': '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4', 'text': 'demo6',
                                                         'readBy': []}}
    for elem in messages.values():
        indata = Message(elem['id'], elem['text'], [])
        db.session.add(indata)
    db.session.commit()




def init_db():
    db.drop_all()
    db.create_all()
    meta = db.metadata
    for table in reversed(meta.sorted_tables):
        print(table)

        db.session.execute(table.delete())



# funkar
def store_message(message):
    new_id = str(uuid.uuid4())
    new_message = Message(new_id, message, [])
    db.session.add(new_message)
    db.session.commit()
    return new_id


# funkar
def get_msg(message_id):
    msg = Message.query.filter_by(id=message_id).first()
    msg_dic = {'id': msg.id, 'msg': msg.msg, 'users': msg.users}
    return msg_dic


# funkar, behöver ett Message obj
def del_msg(message_id):
    db.session.query(Message).filter_by(id=message_id).delete()
    db.session.commit()
    return 200


# funkar, ger en lista
def get_unread(user_id):
    out = []
    all_read = Message.query.filter(Message.users.any(id=user_id)).all()
    all_msg = Message.query.all()
    for msg in all_msg:
        if msg not in all_read:
            msg_dic = {'id': msg.id, 'msg': msg.msg, 'users': msg.users}
            out += [msg_dic]
            # out += [msg]

    return out


# funkar
def mark_read(message_id, userid):
    msg = Message.query.filter_by(id=message_id).first()
    if not User.query.filter_by(id=userid).first():
        new_user = User(int(userid))
        msg.users.append(new_user)
    else:
        current_user = User.query.filter_by(id=userid).first()
        msg.users.append(current_user)

    #print(new_user, " " , msg)
    db.session.commit()
    return 200


# funkar
def get_all_msg():
    all_messages = db.session.query(Message).count()
    return all_messages
# init_db()

# uid1 = store_message('test')
# uid2 = store_message('felix')
# uid3 = store_message('calle')
# mark_read(uid1, 1)
# get_unread(1)

# print(get_msg('220f7259-beb1-4012-aa59-6e787a0cd581'))
