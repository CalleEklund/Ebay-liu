from flask import Flask, jsonify, request
import uuid

app = Flask(__name__)
import db2

messages = {'220f7259-beb1-4012-aa59-6e787a0cd581': {'id': '220f7259-beb1-4012-aa59-6e787a0cd581', 'text': 'demo0',
                                                     'readBy': ['a0d84018-d718-4715-a645-ff375d4b3a13']},
            'a0d84018-d718-4715-a645-ff375d4b3a13': {'id': 'a0d84018-d718-4715-a645-ff375d4b3a13', 'text': 'demo1',
                                                     'readBy': []},
            'afe83630-4cbf-4000-baef-38b13145bf65': {'id': 'afe83630-4cbf-4000-baef-38b13145bf65', 'text': 'demo2',
                                                     'readBy': []},
            '01d6d904-dc32-45fb-9c36-f6c8719cd690': {'id': '01d6d904-dc32-45fb-9c36-f6c8719cd690', 'text': 'demo3',
                                                     'readBy': ['a0d84018-d718-4715-a645-ff375d4b3a13']},
            'a432a82a-6c4b-4c42-b9e0-6713e52b81cb': {'id': 'a432a82a-6c4b-4c42-b9e0-6713e52b81cb', 'text': 'demo4',
                                                     'readBy': []},
            'ef9c846a-3477-4506-8f5c-a7200d5fab71': {'id': 'ef9c846a-3477-4506-8f5c-a7200d5fab71', 'text': 'demo5',
                                                     'readBy': []},
            '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4': {'id': '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4', 'text': 'demo6',
                                                     'readBy': []}}


@app.route('/')
def hello_world():
    return 'hellu erbadies! welcums, to my cribs.'


@app.route('/messages/', methods=['POST', 'GET'])
def message():
    global messages
    if request.method == 'POST':
        msg = request.json['msg']
        uid = uuid.uuid4()
        data = {'id': uid, 'msg': msg, 'readBy': []}
        messages[str(uid)] = data
        outdata = {'id': uid}
        return jsonify(outdata)
    elif request.method == 'GET':
        output = []
        for post in messages.values():
            output.append(post)
        return jsonify(output)


@app.route('/messages/<MessageID>', methods=['DELETE', 'GET'])
def get_message(MessageID):
    global messages

    if request.method == 'GET':
        if MessageID in messages:
            outdata = messages[MessageID]
            return jsonify(outdata)
        else:
            return jsonify({"msg": "Message not found"})
    elif request.method == 'DELETE':
        if MessageID in messages:

            del messages[MessageID]
            return "", 200
        else:
            return jsonify({"msg": "Message not found"}),400


@app.route('/messages/<MessageID>/read/<UserID>', methods=['GET'])
def mark_read(MessageID, UserID):
    global messages
    data = messages[str(MessageID)]
    if data:
        data['readBy'].append(str(UserID))
        return "", 200
    else:
        return jsonify("")


@app.route('/messages/unread/<UserID>', methods=['GET'])
def get_unread(UserID):
    output = []
    for post in messages.values():
        if not UserID in post['readBy']:
            output.append(post)
    return jsonify(output)


@app.route('/init_db')
def init_db():
    db2.init_db()
    return ""


if __name__ == '__main__':
    app.run(debug=True)
