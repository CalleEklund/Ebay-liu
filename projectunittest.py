import os
import tempfile
import pytest
from projektmain import app, db


@pytest.fixture
def client():
    db_fd, app.config['DATABASE_FILE_PATH'] = tempfile.mkstemp()
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///projektdb.db'
    app.config['TESTING'] = True

    client = app.test_client()

    with app.app_context():
        db.drop_all()
        db.create_all()

    yield client

    os.close(db_fd)
    os.unlink('projektdb.db')


def test_empty_db(client):
    """
    Test server connection
    """
    r = client.get('/')
    assert b'liubiljett' in r.data


def test_register_account(client):
    """
    Tests registration of a user
    """
    r = client.post('/user/register/test/testlosen/test@gmail.com')
    assert 'Användare Registerad' in r.get_json()['message']


def test_login_logout(client):
    """
    Tests login of a user and logout
    """
    r = client.post('/user/register/test/testlosen/test@gmail.com')
    login = client.post('/user/login/test@gmail.com/testlosen')
    token = login.get_json()['access_token']
    assert len(login.get_json()['access_token']) == 288

    logout = client.delete('/logout', headers={'Authorization': 'Bearer ' + token})
    assert 'Du är utloggad' in logout.get_json()['msg']


def test_create_like_post(client):
    """
    Tests the creation of a post and error handling of liking a user's own post
    """
    r = client.post('/user/register/test/testlosen/test@gmail.com')
    login = client.post('/user/login/test@gmail.com/testlosen')
    token = login.get_json()['access_token']
    post = client.post('/user/createpost/pytest/100kr/pydesc', headers={'Authorization': 'Bearer ' + token})
    assert 'Inlägg Skapat' in post.get_json()['message']
    like = client.post('/user/likepost/' + str(1), headers={'Authorization': 'Bearer ' + token})
    assert 'Kan inte gilla sitt eget inlägg' in like.get_json()['Error']


def test_add_comment_like_post(client):
    """
    Tests commenting of a post and liking another users post
    """
    r = client.post('/user/register/test/testlosen/test@gmail.com')
    d = client.post('/user/register/test/test2losen/test2@gmail.com')

    login = client.post('/user/login/test@gmail.com/testlosen')
    token = login.get_json()['access_token']
    post = client.post('/user/createpost/pytest/100kr/pydesc', headers={'Authorization': 'Bearer ' + token})

    login = client.post('/user/login/test2@gmail.com/test2losen')
    token = login.get_json()['access_token']
    comment = client.post('/user/comment/' + str(1) + '/testcom', headers={'Authorization': 'Bearer ' + token})
    assert 'Inlägg kommenterat' in comment.get_json()['Message']

    like = client.post('/user/likepost/' + str(1), headers={'Authorization': 'Bearer ' + token})
    assert 'Inlägg Gillat' == like.get_json()['Message']
    unlike = client.post('/user/unlikepost/' + str(1), headers={'Authorization': 'Bearer ' + token})
    assert 'Inlägg Ogillat' in unlike.get_json()['Message']


def test_follow_unfollow(client):
    """
    Tests to follow and unfollow a user
    """
    r = client.post('/user/register/test/testlosen/test@gmail.com')
    d = client.post('/user/register/test/test2losen/test2@gmail.com')
    login = client.post('/user/login/test2@gmail.com/test2losen')
    token = login.get_json()['access_token']
    follow = client.post('/user/followuser/' + str(1), headers={'Authorization': 'Bearer ' + token})
    assert  'Användare följd' == follow.get_json()['Message']

    unfollow = client.post('/user/unfollowuser/' + str(1), headers={'Authorization': 'Bearer ' + token})
    assert 'Du följer inte längre användaren' == unfollow.get_json()['Message']
