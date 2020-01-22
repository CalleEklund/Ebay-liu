import requests
import json

def save_msg():
    r = requests.post("http://127.0.0.1:5000/messages")
    if check_ok(r) == "ok":
        data = r.json()
        return data

def get_msg():
    r = requests.get("http://127.0.0.1:5000/messages/a0d84018-d718-4715-a645-ff375d4b3a13")
    if check_ok(r) == "ok":
        print(r.json())

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
        return "ok"

get_msg()
