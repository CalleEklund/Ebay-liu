import requests


def register_user():
    r = requests.post("http://127.0.0.1:5000/user/register/calle/losen")
    print(r.text)


def login_user():
    r = requests.post("http://127.0.0.1:5000/user/login/calle/losen")
    access_token = r.json()
    return access_token['access_token']

def logout_user(token):
    r = requests.post("http://127.0.0.1:5000/user/logout",headers={'Authorization': 'Bearer ' + token})
    print(r.text)

def protected_page(token):
    r = requests.post("http://127.0.0.1:5000/protected", headers={'Authorization': 'Bearer ' + token})
    print(r.text)


register_user()
token = login_user()
protected_page(token)
logout_user(token)
protected_page(token)
#print(token)

