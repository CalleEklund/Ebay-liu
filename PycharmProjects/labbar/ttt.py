import requests


def save_msg():
    payload = {"message": "hello!"}
    r = requests.post("http://127.0.0.1:5000/message", json=payload)
    return r.json()['id']
    # print()


def get_msg(msg_id):
    r = requests.get("http://127.0.0.1:5000/message/" + msg_id)
    data = r.json()
    print(data)


def delete_msg(msg_id):
    d = requests.delete("http://127.0.0.1:5000/message/"+msg_id)
    data_d = d.status_code
    print(data_d)
