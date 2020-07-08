import requests


def start_test():
    d = requests.get("https://liubiljett.herokuapp.com")
    print(d.text)


def register_user(name, password, email, section):
    r = requests.post(
        "https://liubiljett.herokuapp.com/user/register/" + name + "/" + password + "/" + email + "/" + section)

    print(r.text)


register_user("calle", "losen", "calle@gmail.com", "desektionen")
# start_test()