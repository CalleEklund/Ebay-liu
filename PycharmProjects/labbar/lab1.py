import requests


def save_msg():
    r = requests.post("http://127.0.0.1:5000/messages",json={"msg" : "hello!"})
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def get_all_msg():
    g = requests.get("http://127.0.0.1:5000/messages")
    if check_ok(g):
        data_g = g.json
        return data_g
    else:
        return check_ok(g)

def get_msg():
    r = requests.get("http://127.0.0.1:5000/messages/676941a1-5c4f-4d62-b9d5-de2b2d46d7e1")
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def delete_msg():
    d = requests.delete("http://127.0.0.1:5000/messages/a0d84018-d718-4715-a645-ff375d4b3a13")
    if check_ok(d):
        data_d = d[1]
        return data_d
    else:
        return check_ok(d)

def mark_read():
    g = requests.get("http://127.0.0.1:5000/messages/220f7259-beb1-4012-aa59-6e787a0cd581/read/a0d84018-d718-4715-a645-ff375d4b3a13")
    if check_ok(g):
        data_d = g[1]
        return data_d
    else:
        return check_ok(g)

def get_unread():
    g = requests.get("http://127.0.0.1:5000/messages/unread/a0d84018-d718-4715-a645-ff375d4b3a13")
    if check_ok(g):
        return g.json()

def check_ok(req):
    if req.status_code == 404:
        return "The request does not map to a function"
    elif req.status_code == 405:
        return "Using wrong method"
    elif req.status_code == 400:
        return "Wrongfully parameters or missing"
    elif req.status_code == 500:
        return "Error"
    else:
        return True

#testa metoderna här:
print(delete_msg())
