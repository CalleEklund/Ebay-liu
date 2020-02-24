import requests

import requests


def save_msg():
    payload = {"message": "hello!"}
    r = requests.post("http://127.0.0.1:5000/message", json=payload)
    return r.json()

def get_all():
    r = requests.get("http://127.0.0.1:5000/message")
    return r.json()

def get_msg(msg_id):
    r = requests.get("http://127.0.0.1:5000/message/" + str(msg_id))
    data = r.json()
    return data

def delete_msg(msg_id):
    d = requests.delete("http://127.0.0.1:5000/message/" + str(msg_id))
    data_d = d.status_code
    return data_d

def mark_read(msg_id, user_id):
    r = requests.post("http://127.0.0.1:5000/message/" + str(msg_id) + '/read/' + str(user_id))
    data_r = r.status_code
    return data_r

def get_unread(user_id):
    r = requests.get("http://127.0.0.1:5000/message/unread/" + str(user_id))
    print(r)


uid = "test"
uid = save_msg()['id']
uid1 = save_msg()['id']
uid2 = save_msg()['id']
print(uid)
#print("before",get_msg(uid))
mark_read(uid,1)
# print("after",get_msg(uid))
get_unread(1)