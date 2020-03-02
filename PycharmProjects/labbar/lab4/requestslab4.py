import requests


def register_user(name,password):
    r = requests.post("http://127.0.0.1:5000/user/register/"+name+"/"+password)
    print(r.text)


def login_user(name,password):
    r = requests.post("http://127.0.0.1:5000/user/login/"+name+"/"+password)
    access_token = r.json()
    return access_token["access_token"]


def logout_user(token):
    r = requests.post("http://127.0.0.1:5000/user/logout", headers={'Authorization': 'Bearer ' + token})
    print(r.text)


def protected_page(token):
    r = requests.post("http://127.0.0.1:5000/protected", headers={'Authorization': 'Bearer ' + token})
    print(r.text)


def save_msg(token):
    payload = {"message": "hello!"}
    r = requests.post("http://127.0.0.1:5000/message", json=payload, headers={'Authorization': 'Bearer ' + token})
    return r.json()['id']


def get_all_msg():
    r = requests.get("http://127.0.0.1:5000/message")
    return r.json()


def get_msg(msg_id):
    r = requests.get("http://127.0.0.1:5000/message/" + str(msg_id))
    data = r.json()
    return data


def delete_msg(msg_id,token):
    d = requests.delete("http://127.0.0.1:5000/message/" + msg_id, headers={'Authorization': 'Bearer ' + token})
    data_d = d.status_code
    print(data_d)


def mark_read(token,msg_id, username):
    r = requests.get("http://127.0.0.1:5000/message/" + str(msg_id) + '/read/' + str(username),
                     headers={'Authorization': 'Bearer ' + token})
    data_r = r.status_code
    print(data_r)


def get_unread(token,user_name):
    r = requests.post("http://127.0.0.1:5000/message/unread/" + str(user_name),
                      headers={'Authorization': 'Bearer ' + token})
    print(r.json())


register_user("felix","losen")
register_user("calle","losen")
tokenf = login_user("felix","losen")
tokenc = login_user("calle","losen")

idf = save_msg(tokenf)
idc = save_msg(tokenc)

print(get_all_msg())

#mark_read(tokenf,idf,"felix")
#mark_read(tokenc,idc,"calle")
#get_unread(tokenf,"felix")

