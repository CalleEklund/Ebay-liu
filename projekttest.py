import requests


def start_test():
    d = requests.get("https://liubiljett.herokuapp.com")
    print(d.text)


def register_user(name, password, email, section):
    r = requests.post(
        "https://liubiljett.herokuapp.com/user/register/" + name + "/" + password + "/" + email + "/" + section)

    print(r.text)

def login_user(email,password):
    r = requests.get("https://liubiljett.herokuapp.com/user/login/"+email+"/"+password)
    print(r.text)

def get_all_test():
    r = requests.get("https://liubiljett.herokuapp.com/user/all")
    print(r.text)

login_user("calle@gmail.com","losen")
# get_all_test()
# register_user("calle", "losen", "calle@gmail.com", "desektionen")
# start_test()