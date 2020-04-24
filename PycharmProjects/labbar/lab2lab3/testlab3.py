import requests


def save_msg():
    r = requests.post("https://liubiljett.herokuapp.com/message", json={"message" : "hello!"})
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def get_all_msg():
    g = requests.get("https://liubiljett.herokuapp.com/message")
    if check_ok(g):
        data_g = g.json()
        return data_g
    else:
        return check_ok(g)

def get_msg(uid):
    r = requests.get("https://liubiljett.herokuapp.com/message/"+str(uid))

    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def delete_msg(uid):
    d = requests.delete("https://liubiljett.herokuapp.com/message/"+str(uid))
    if check_ok(d):
        data_d = d.status_code
        return data_d
    else:
        return check_ok(d)

def mark_read(msgid,userid):
    g = requests.get("https://liubiljett.herokuapp.com/message/"+str(msgid)+"/read/"+str(userid))
    if check_ok(g):
        data_d = g.status_code
        return data_d
    else:
        return check_ok(g)

def get_unread(userid):
    g = requests.get("https://liubiljett.herokuapp.com/message/unread/"+str(userid))
    if check_ok(g):
        return g

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
#uid = save_msg()
#uid2 = save_msg()
#print(uid['id'])
#print(get_msg(uid['id']))
#print(delete_msg(uid['id']))
#print(mark_read(uid['id'],1))
print(get_all_msg())
#print(get_unread(1))

#maila länk och lägg till som reporter
