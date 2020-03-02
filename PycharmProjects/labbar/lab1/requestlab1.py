import requests

#test från laptop calle
def save_msg():
    r = requests.post("http://127.0.0.1:5000/messages/", json={"msg" : "hello!"})

    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def get_all_msg():
    g = requests.get("http://127.0.0.1:5000/messages/")
    if check_ok(g):
        data_g = g.json()
        return data_g
    else:
        return check_ok(g)

def get_msg(uid):
    r = requests.get("http://127.0.0.1:5000/messages/"+uid)
    if check_ok(r):
        data = r.json()
        return data
    else:
        return check_ok(r)

def delete_msg(uid):
    d = requests.delete("http://127.0.0.1:5000/messages/"+uid)
    if check_ok(d):
        data_d = d
        return data_d.json(),data_d.status_code
    else:
        return check_ok(d)

def mark_read(uid,user_id):
    g = requests.get("http://127.0.0.1:5000/messages/"+uid+"/read/"+user_id)
    if check_ok(g):
        data_d = g.status_code
        return data_d
    else:
        return check_ok(g)

def get_unread(user_id):
    g = requests.get("http://127.0.0.1:5000/messages/unread/"+user_id)
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
id = "test"
user_test_id = str(1)
#id = save_msg()['id']
#print(id)
print(delete_msg(id))
#print(mark_read(id,user_test_id))
# print(get_msg(id))
#print(get_unread(user_test_id))
#print(get_all_msg())


#maila länk och lägg till som reporter
