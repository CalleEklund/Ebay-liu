import os
import tempfile
import pytest
from lab2server import app, db2


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
    r = client.post('/message', json=payload)

    res = r.get_json()

    assert len(res['id']) == 36

def test_get_message(client):
    payload = {'message': 'hi'}
    r = client.post('/message', json=payload)
    new_id = r.get_json()['id']

    g = client.get('/message/'+new_id)
    assert g.get_json()

def test_get_all_messages(client):
    payload = {'message': 'hi'}
    r = client.post('/message', json=payload)
    message_id = r.get_json()
    assert len(message_id['id']) == 36

    payload = {'message': 'there'}
    r = client.post('/message', json=payload)
    message_id = r.get_json()
    assert len(message_id['id']) == 36

    r = client.get('/message')
    all_messages = r.get_json()
    assert all_messages == 2

def test_del_message(client):
    payload = {'message': 'hi'}
    r = client.post('/message', json=payload)
    new_id = r.get_json()['id']

    d = client.delete('/message/'+new_id)
    assert d.status_code == 200


def test_mark_read(client):
    payload = {'message': 'hi'}
    r = client.post('/message', json=payload)
    new_id = r.get_json()['id']
    user_id = 1
    mr = client.post('/message/'+new_id+'/read/'+str(user_id))
    assert mr.status_code == 200



