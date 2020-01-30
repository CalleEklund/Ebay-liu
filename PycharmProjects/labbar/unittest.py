import os
import tempfile
import pytest
from app2 import app
import db2


@pytest.fixture
def client():
    db_fd, app.config['DATABASE_FILE_PATH'] = tempfile.mkstemp()
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + app.config['DATABASE_FILE_PATH']
    app.config['TESTING'] = True

    client = app.test_client()

    with app.app_context():
        db2.init_db()
        test_empty_db(client)

    yield client

    os.close(db_fd)
    os.unlink(app.config['DATABASE_FILE_PATH'])


def test_empty_db(client):
    r = client.get('/')
    print("test")
    assert b'hellu erbadies! welcums, to my cribs.' in r.data


# def test_save_message(client):
#     payload = {'message': 'hello'}
#     r = client.post('/messages', json=payload, content_type='application/json')
#     message_id = str(r.data.decode(encoding='utf-8'))
#     assert len(message_id) == 8
#
# def test_get_all_messages(client):
#     payload = {'message': 'hi'}
#     r = client.post('/messages', json=payload, content_type='application/json')
#     message_id = str(r.data.decode(encoding='utf-8'))
#     assert len(message_id) == 8
#     payload = {'message': 'there'}
#     r = client.post('/messages', json=payload, content_type='application/json')
#     message_id = str(r.data.decode(encoding='utf-8'))
#     assert len(message_id) == 8
#     r = client.get('/messages')
#     all_messages = r.get_json()
#     assert(len(all_messages)) == 2

