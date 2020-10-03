import requests


def start_test():
    d = requests.get("http://127.0.0.1:5000")
    return d.text


def register_user(name, password, email):
    d = requests.post(
        "http://127.0.0.1:5000/user/register/" + str(name) + "/" + str(password) + "/" + str(email))
    return d.json()


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
                      headers={"Authorization": "Bearer " + token})
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
                        headers={'Authorization': 'Bearer ' + token})
    return r.json()


def get_feed():
    r = requests.post("http://127.0.0.1:5000/post/all")
    return r.json()


def add_comment(post_id, comment, token):
    r = requests.post("http://127.0.0.1:5000/user/comment/" + str(post_id) + "/" + str(comment),
                      headers={'Authorization': 'Bearer ' + token})
    return r.json()


def get_post_creator(post_id):
    r = requests.post("http://127.0.0.1:5000/post/getcreator/" + str(post_id))
    return r.json()


def follow_user(user_id, token):
    r = requests.post("http://127.0.0.1:5000/user/followuser/" + str(user_id),
                      headers={'Authorization': 'Bearer ' + token})
    return r.json()


def unfollow_user(user_id, token):
    r = requests.post("http://127.0.0.1:5000/user/unfollowuser/" + str(user_id),
                      headers={'Authorization': 'Bearer ' + token})
    return r.json()

def followed_posts(token):
    r = requests.post("http://127.0.0.1:5000/user/getfollowedpost",
                      headers={'Authorization': 'Bearer ' + token})
    return r.json()


