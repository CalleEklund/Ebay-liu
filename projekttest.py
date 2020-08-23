import requests


# def start_test():
#     d = requests.get("https://liubiljett.herokuapp.com")
#     print(d.text)
#
#
# def register_user(name, password, email, section):
#     r = requests.post(
#         "https://liubiljett.herokuapp.com/user/register/" + name + "/" + password + "/" + email + "/" + section)
#
#     print(r.text)
#
# def login_user(email,password):
#     r = requests.get("https://liubiljett.herokuapp.com/user/login/"+email+"/"+password)
#     print(r.text)
#
# def get_all_test():
#     r = requests.get("https://liubiljett.herokuapp.com/user/all")
#     print(r.text)
#
# # login_user("calle@gmail.com","losen")
# # get_all_test()
# register_user("calle", "losen", "calle@gmail.com", "desektionen")
# # start_test()

def start_test():
    d = requests.get("http://127.0.0.1:5000")
    return d.text


def register_user(name, password, email, section):
    d = requests.post(
        "http://127.0.0.1:5000/user/register/" + str(name) + "/" + str(password) + "/" + str(email) + "/" + str(
            section))
    return d.text


def login_user(email, password):
    r = requests.post("http://127.0.0.1:5000/user/login/" + str(email) + "/" + str(password))
    access_token = r.json()
    return access_token


def all_users(token):
    r = requests.get("http://127.0.0.1:5000/user/all", headers={'Authorization': 'Bearer ' + token})
    return r.text


# print(start_test())
# print(register_user("calle", "losen", "test@gmail.com", "dsektionen"))
# print(register_user("felix","felixlosen","felix@gmail.com","dsektionen"))
token = login_user("test@gmail.com", "losen")['access_token']
print(all_users(token))
