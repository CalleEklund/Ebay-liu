from flask import Flask,jsonify, request
from flask_sqlalchemy import SQLAlchemy


app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///lab2.db'

db = SQLAlchemy(app)

class User(db.Model):
    id = db.Column(db.Integer,primary_key=True,unique=True)
    msg = db.Column(db.String(140),nullable=False)

    def __repr__(self):
        return '<User %r %l>' % self.id+self.msg


def setup():
    db.create_all()
    u1 = User(username='test1')
    u2 = User(username='test2')

    db.session.add(u1)
    db.session.add(u2)
    db.session.commit()

def intial_insert():
    messages = {'220f7259-beb1-4012-aa59-6e787a0cd581': {'id': '220f7259-beb1-4012-aa59-6e787a0cd581', 'text': 'demo0',
                                                         'readBy': ['a0d84018-d718-4715-a645-ff375d4b3a13']},
                'a0d84018-d718-4715-a645-ff375d4b3a13': {'id': 'a0d84018-d718-4715-a645-ff375d4b3a13', 'text': 'demo1',
                                                         'readBy': []},
                'afe83630-4cbf-4000-baef-38b13145bf65': {'id': 'afe83630-4cbf-4000-baef-38b13145bf65', 'text': 'demo2',
                                                         'readBy': []},
                '01d6d904-dc32-45fb-9c36-f6c8719cd690': {'id': '01d6d904-dc32-45fb-9c36-f6c8719cd690', 'text': 'demo3',
                                                         'readBy': ['a0d84018-d718-4715-a645-ff375d4b3a13']},
                'a432a82a-6c4b-4c42-b9e0-6713e52b81cb': {'id': 'a432a82a-6c4b-4c42-b9e0-6713e52b81cb', 'text': 'demo4',
                                                         'readBy': []},
                'a432a82a-6c4b-4c42-b9e0-6713e52b81cb': {'id': 'a432a82a-6c4b-4c42-b9e0-6713e52b81cb', 'text': 'demo4',
                                                         'readBy': []},
                'ef9c846a-3477-4506-8f5c-a7200d5fab71': {'id': 'ef9c846a-3477-4506-8f5c-a7200d5fab71', 'text': 'demo5',
                                                         'readBy': []},
                '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4': {'id': '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4', 'text': 'demo6',
                                                         'readBy': []}}


def clear_data(session):
    meta = db.metadata
    for table in reversed(meta.sorted_tables):
        print('Clear table %s' % table)
        session.execute(table.delete())
    session.commit()
#använda för att ta bort överflödig data.
#clear_data(db.session)

if __name__ == '__main__':
    app.run(debug=True)


