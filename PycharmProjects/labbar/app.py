from flask import Flask, jsonify, request
import uuid


app = Flask(__name__)


# global dict

messages = [{'id': '220f7259-beb1-4012-aa59-6e787a0cd581', 'text': 'demo0', 'readBy': []}, {'id': 'a0d84018-d718-4715-a645-ff375d4b3a13', 'text': 'demo1', 'readBy': []}, {'id': 'afe83630-4cbf-4000-baef-38b13145bf65', 'text': 'demo2', 'readBy': []}, {'id': '01d6d904-dc32-45fb-9c36-f6c8719cd690', 'text': 'demo3', 'readBy': []}, {'id': 'a432a82a-6c4b-4c42-b9e0-6713e52b81cb', 'text': 'demo4', 'readBy': []}, {'id': 'ef9c846a-3477-4506-8f5c-a7200d5fab71', 'text': 'demo5', 'readBy': []}, {'id': '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4', 'text': 'demo6', 'readBy': []}]



@app.route('/')
def hello_world():
    return 'Hellus eVerYbodieS!'


@app.route('/messages/',methods=['POST', 'GET'])
def message():
    if request.method == 'POST':
        msg = "demo7"
        id = uuid.uuid4()
        data = {'id': id, 'text': msg, 'readBy': []}
        outdata = {'id': id}
        messages.append(data)
        return jsonify(outdata)
    elif request.method == 'GET':
        return jsonify(messages)


@app.route('/messages/<MessageID>/read/<UserID>', methods=['GET'])
def mark_read(MessageID, UserID):
    yete = get_id_msg(MessageID, None)
    if yete:
        yete['readBy'].append(str(UserID))
        return jsonify("")
    else:
        return jsonify("")
    print(messages)

@app.route('/messages/<MessageID>',methods=['DELETE','GET'])
def get_message(MessageID):
    id = MessageID
    print(request.method)

    if request.method == 'GET':
        out = get_id_msg(id,None)
        return jsonify(out)
    elif request.method == 'DELETE':

        elem = get_id_msg(id, None)
        if elem:
            messages.remove(elem)
            return jsonify("test")
        else:
            return jsonify("test")


def get_id_msg(id=None,msg=None):
    for elem in messages:
        if id in elem.values() or msg in elem.values():
            return elem

    return None


if __name__ == '__main__':
    app.run(debug=True)
