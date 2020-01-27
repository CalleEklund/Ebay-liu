from flask import Flask,jsonify, request
from flask_sqlalchemy import SQLAlchemy


app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///lab2.db'

db = SQLAlchemy(app)

users_messages = db.Table('users_messages',
                          db.Column('user_id',db.Integer,db.ForeignKey('user.id'),primary_key=True),
                          db.Column('message_readBy',db.Integer,db.ForeignKey('message.readBy'),primary_key=True)
                          )

class User(db.Model):
    __tabelname__ = 'users'
    id = db.Column(db.Integer,primary_key=True,autoincrement=True)

    messages = db.relationship('Message',secondary=users_messages,back_populates='users')

class Message(db.Model):
    __tablename__ = 'message'
    id = db.Column(db.String(36),primary_key=True)
    msg = db.Column(db.String(140),nullable=False)
    readBy = db.Column(db.Integer,nullable=True)

    users = db.relationship('User',secondary=users_messages,back_populates='messages')

#db.create_all()

def intial_insert():

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
                'a432a82a-6c4b-4c42-b9e0-6713e52b81cb': {'id': 'a432a82a-6c4b-4c42-b9e0-6713e52b81cb', 'text': 'demo4',
                                                         'readBy': []},
                'ef9c846a-3477-4506-8f5c-a7200d5fab71': {'id': 'ef9c846a-3477-4506-8f5c-a7200d5fab71', 'text': 'demo5',
                                                         'readBy': []},
                '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4': {'id': '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4', 'text': 'demo6',
                                                         'readBy': []}}
    for elem in messages.values():
        indata = Message(id = elem['id'],msg = elem['text'],readBy=None)
        db.session.add(indata)
    db.session.commit()

intial_insert()

def clear_data(session):
    meta = db.metadata
    for table in reversed(meta.sorted_tables):
        print('Clear table %s' % table)
        session.execute(table.delete())
    session.commit()
#använda för att ta bort överflödig data.
#clear_data(db.session)

#if __name__ == '__main__':
#    app.run(debug=True)


