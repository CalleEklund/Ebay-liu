import requests


def save_msg():
    #g = requests.get("http://127.0.0.1:5000/messages") or None

    r = requests.post("http://127.0.0.1:5000/messages",json={"msg" : "hello!"}) or None
    """
    if check_ok(r) and check_ok(g):
        data_r = r.json()
        data_g = g.json()
        return data_r,data_g
    el
    
     elif check_ok(g):
        data = g.json()
        return data
    """
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)


def get_unread():
    g = requests.get("http://127.0.0.1:5000/messages/unread/a0d84018-d718-4715-a645-ff375d4b3a13")
    if check_ok(g):
        print(g.status_code)
        return g.json()


def get_msg():
    r = requests.get("http://127.0.0.1:5000/messages/676941a1-5c4f-4d62-b9d5-de2b2d46d7e1")

    #d = requests.delete("http://127.0.0.1:5000/messages/a0d84018-d718-4715-a645-ff375d4b3a13")
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)
"""
    if check_ok(r) and check_ok(d):
        data_r = r.json()
        data_d = d.status_code()
        return data_r,data_d
    elif check_ok(r):
        data = r.json()
        return data
    elif check_ok(d):
        return d.status_code
    else:
        print(check_ok(d),check_ok(r))
"""

def check_ok(r):
    if r.status_code == 404:
        return "The request does not map to a function"
    elif r.status_code == 405:
        return "Using wrong method"
    elif r.status_code == 400:
        return "Wrongfully parameters or missing"
    elif r.status_code == 500:
        return "Error"
    else:
        return True


print(get_msg())
#print(save_msg())
