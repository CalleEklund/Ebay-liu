import requests


def register_user():
    r = requests.post("http://127.0.0.1:5000/user/register/calle/losen")
    print(r.text)


def login_user():
    r = requests.post("http://127.0.0.1:5000/user/login/calle/losen")
    access_token = r.json()
    return access_token["access_token"]
    # return access_token

def logout_user(token):
    r = requests.post("http://127.0.0.1:5000/user/logout",headers={'Authorization': 'Bearer ' + token})
    print(r.text)

def protected_page(token):
    r = requests.post("http://127.0.0.1:5000/protected", headers={'Authorization': 'Bearer ' + token})
    print(r.text)

def save_msg(token):
    payload = {"message": "hello!"}
    # print('token',token)
    r = requests.post("http://127.0.0.1:5000/message", json=payload, headers={'Authorization': 'Bearer ' + token})
    return r.json()['id']

def get_all_msg_count():
    r = requests.get("http://127.0.0.1:5000/message")
    return r.json()


def get_msg(msg_id):
    r = requests.get("http://127.0.0.1:5000/message/" + str(msg_id))
    data = r.json()
    return data


def delete_msg(msg_id):
    d = requests.delete("http://127.0.0.1:5000/message/" + msg_id, headers={'Authorization': 'Bearer ' + token})
    data_d = d.status_code
    print(data_d)


def mark_read(msg_id, username, password):
    r = requests.get("http://127.0.0.1:5000/message/" + str(msg_id) + '/read/' + str(username), headers={'Authorization': 'Bearer ' + token})
    data_r = r.status_code
    print(data_r)

def get_unread(user_id):
    r = requests.post("http://127.0.0.1:5000/message/unread/" + str(user_id), headers={'Authorization': 'Bearer ' + token})
    print(r.json())


register_user()
token = login_user()
# print(token)
protected_page(token)
uid = save_msg(token)
print(get_msg(uid))
# uid2 = save_msg(token)
# mark_read(uid, "felix", "test")
# logout_user(token)
# protected_page(token)
# print(token)

