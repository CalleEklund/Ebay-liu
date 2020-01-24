import requests


def save_msg():
    r = requests.post("http://127.0.0.1:5000/messages", json={"msg" : "hello!"})
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def get_all_msg():
    g = requests.get("http://127.0.0.1:5000/messages")
    if check_ok(g):
        data_g = g.json()
        return data_g
    else:
        return check_ok(g)

def get_msg():
    r = requests.get("http://127.0.0.1:5000/messages/9f5dccef-4e78-4cc7-b9c7-bd8fa383ab86")
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def delete_msg():
    d = requests.delete("http://127.0.0.1:5000/messages/f036ba77-a224-4180-be03-dc37ed8f74db")
    if check_ok(d):
        data_d = d.status_code
        return data_d
    else:
        return check_ok(d)

def mark_read():
    g = requests.get("http://127.0.0.1:5000/messages/220f7259-beb1-4012-aa59-6e787a0cd581/read/afe83630-4cbf-4000-baef-38b13145bf65")
    if check_ok(g):
        data_d = g.status_code
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

# testa metoderna här:
# print(save_msg())
# print(get_msg())
# print(delete_msg())
# print(mark_read())
# print(get_all_msg())
# print(get_unread())

#maila länk och lägg till som reporter
