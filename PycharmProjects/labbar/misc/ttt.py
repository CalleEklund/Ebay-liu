import requests


def save_msg():
    payload = {"message": "hello!"}
    #print('test')
    r = requests.post("http://127.0.0.1:5000/message", json=payload)
    return r.json()['id']
    # print()


def get_msg(msg_id):
    r = requests.get("http://127.0.0.1:5000/message/" + msg_id)
    data = r.json()
    print(data)


def delete_msg(msg_id):
    d = requests.delete("http://127.0.0.1:5000/message/" + msg_id)
    data_d = d.status_code
    print(data_d)


def mark_read(msg_id, user_id):
    r = requests.get("http://127.0.0.1:5000/message/" + str(msg_id) + '/read/' + str(user_id))
    data_r = r.status_code
    print(data_r)

def get_unread(user_id):
    r = requests.post("http://127.0.0.1:5000/message/unread/" + str(user_id))
    print(r.json())

uid = save_msg()
# uid2 = save_msg()
# mark_read(uid,1)
# get_unread(1)
