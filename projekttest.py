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


def logout_current_user(token):
    r = requests.delete("http://127.0.0.1:5000/logout", headers={'Authorization': 'Bearer ' + token})
    return r.json()


def all_users(token):
    r = requests.get("http://127.0.0.1:5000/user/all", headers={'Authorization': 'Bearer ' + token})
    return r.text


def get_curr_user(token):
    r = requests.post("http://127.0.0.1:5000/user/current", headers={'Authorization': 'Bearer ' + token})
    return r.json()


def create_post(token, post_title, post_price, post_description):
    r = requests.post("http://127.0.0.1:5000/user/createpost/" + post_title + "/" + post_price + "/" + post_description,
                     headers={'Authorization': 'Bearer ' + token})
    return r.json()


def like_post(token, post_id):
    r = requests.post("http://127.0.0.1:5000/user/likepost/" + str(post_id),
                     headers={'Authorization': 'Bearer ' + token})
    return r.json()

def unlike_post(token, post_id):
    r = requests.post("http://127.0.0.1:5000/user/unlikepost/" + str(post_id),
                     headers={'Authorization': 'Bearer ' + token})
    return r.json()


def get_user(email):
    r = requests.post('http://127.0.0.1:5000/user/get/' + email)
    return r.json()


def delete_post(token, post_id):
    r = requests.delete("http://127.0.0.1:5000/user/deletepost/" + str(post_id),
                        headers={'Authorization': 'Bearer' + token})
    return r.json()


def get_feed():
    r = requests.post("http://127.0.0.1:5000/post/all")
    return r.json()


# print(register_user("felix","felixlosen","felix@gmail.com"))
# print(register_user("dbtest", "dblosen", "db@gmail.com"))
token = login_user('calle@gmail.com', 'callelosen')
# print(get_curr_user(token))
# print(token)
# token = login_user('felix@gmail.com', 'felixlosen')

# print(create_post(token,'gilla','10kr','snälla'))
# print(get_user('calle@gmail.com'))

# print(get_user('felix@gmail.com'))
print(like_post(token, 3))
# print(unlike_post(token,3))
# print(delete_post(token,1))
# print(get_feed())
