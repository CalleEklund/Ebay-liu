import os
import tempfile
import pytest
from app2 import app, db2
import json


# import db2


@pytest.fixture
def client():
    db_fd, app.config['DATABASE_FILE_PATH'] = tempfile.mkstemp()
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + app.config['DATABASE_FILE_PATH']
    app.config['TESTING'] = True

    client = app.test_client()

    with app.app_context():
        db2.init_db()

    yield client

    os.close(db_fd)
    os.unlink(app.config['DATABASE_FILE_PATH'])


def test_empty_db(client):
    r = client.get('/')
    assert b'hellu erbadies! welcums, to my cribs.' in r.data


def test_save_message(client):
    payload = {'message': 'hello'}
    r = json.loads(client.post('/message', json=payload, content_type='application/json'))
    print("=============================")
    print(r)
    assert len(r['id']) == 36

#def test_get_message(client):
    #payload = {'message': 'hi'}
    #r = client.post('/message', json=payload, content_type='application/json')
    #message_id = r['id']
    '''d = client.post('/delete/'+message_id)
    print(d)'''

    #assert 200 == 200

'''def test_get_all_messages(client):
    payload = {'message': 'hi'}
    r = client.post('/message', json=payload, content_type='application/json')
    message_id = str(r.data.decode(encoding='utf-8'))
    assert len(message_id) == 36
    payload = {'message': 'there'}
    r = client.post('/message', json=payload, content_type='application/json')
    message_id = str(r.data.decode(encoding='utf-8'))
    assert len(message_id) == 36
    r = client.get('/message')
    all_messages = r.get_json()
    assert all_messages == 2'''


