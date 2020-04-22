from flask import Flask, jsonify, request

app = Flask(__name__)
import lab2db as db2


@app.route('/')
def hello_world():
    return 'hello erbadies! welcums, to my.'


@app.route('/message', methods=['POST', 'GET'])
def message():
    if request.method == 'POST':
        msg = request.json['message']
        if len(msg) > 140:
            return "", 400
        message_obj = db2.save_message(msg)

        return jsonify(message_obj)

    elif request.method == 'GET':
        all_messages = db2.all_messages()
        return jsonify(all_messages)


@app.route('/init_db')
def init_db():
    db2.init_db()
    return ""


@app.route('/message/<MessageID>', methods=['DELETE', 'GET'])
def get_message(MessageID):
    if request.method == 'GET':
        msg_obj = db2.get_message(MessageID)
        if msg_obj['id'] is None:
            return "", 404
        return msg_obj
    if request.method == 'DELETE':
        msg_id = MessageID
        if not msg_id:
            return "", 404
        out = db2.del_message(msg_id)
        return out


@app.route('/message/<MessageID>/read/<UserID>', methods=['POST'])
def mark_read(MessageID, UserID):
    if request.method == 'POST':
        msg_id = MessageID
        user_id = UserID
        if not msg_id or not user_id:
            return "", 404
        db2.mark_read(str(msg_id), str(user_id))
        return "", 200


@app.route('/message/unread/<UserID>', methods=['GET'])
def get_unread(UserID):
    if request.method == 'GET':
        user_id = UserID
        output = db2.get_unread(user_id)
        return jsonify(output)


if __name__ == '__main__':
    db2.init_db()
    app.run(port=5000, debug=True)
