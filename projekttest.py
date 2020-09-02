import requests


def start_test():
    d = requests.get("http://127.0.0.1:5000")
    return d.text


def register_user(name, password, email):
    d = requests.post(
        "http://127.0.0.1:5000/user/register/" + str(name) + "/" + str(password) + "/" + str(email))
    return d.text


def login_user(email, password):
    r = requests.post("http://127.0.0.1:5000/user/login/" + str(email) + "/" + str(password))
    access_token = r.json()['access_token']
    return access_token


def all_users(token):
    r = requests.get("http://127.0.0.1:5000/user/all", headers={'Authorization': 'Bearer ' + token})
    return r.text


def get_curr_user(token):
    r = requests.post("http://127.0.0.1:5000/getcurrentuser", headers={'Authorization': 'Bearer ' + token})
    return r.json()


def create_post(token):
    post_config = '/Sådärja/50kr/ajjemän'
    post_config1 = '/Så/50kr/därja'
    r = requests.get("http://127.0.0.1:5000/user/createpost" + post_config,
                     headers={'Authorization': 'Bearer ' + token})
    return r.json()

def like_post(token,post_id):
    r = requests.get("http://127.0.0.1:5000/user/likepost/"+str(post_id) ,
                     headers={'Authorization': 'Bearer ' + token})
    return r.json()

def get_user(email):
    r = requests.post('http://127.0.0.1:5000/user/get/' + email)
    return r.json()


# print(start_test())
# print(register_user("felix","felixlosen","felix@gmail.com"))
# print(register_user("dbtest", "dblosen", "db@gmail.com"))
token = login_user('felix@gmail.com', 'felixlosen')
# print(create_post(token))
# print(get_user('db@gmail.com'))
print(get_user('felix@gmail.com'))
# print(like_post(token,1))