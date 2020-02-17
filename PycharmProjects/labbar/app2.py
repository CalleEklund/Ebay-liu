from flask import Flask, jsonify, request

app = Flask(__name__)
import db2


@app.route('/')
def hello_world():
    return 'hellu erbadies! welcums, to my cribs.'


@app.route('/message', methods=['POST', 'GET'])
def message():
    if request.method == 'POST':
        msg = request.json['message']
        if len(msg) > 140:
            return "", 400
        new_id = db2.store_message(msg)
        outdata = {'id': new_id}
        return jsonify(outdata)

    elif request.method == 'GET':
        all_messages = db2.get_all_msg()
        return jsonify(all_messages)


@app.route('/init_db')
def init_db():
    db2.init_db()
    return ""


@app.route('/message/<MessageID>', methods=['DELETE', 'GET'])
def get_message(MessageID):
    if request.method == 'GET':
        msg_obj = db2.get_msg(str(MessageID))
        #print(msg_obj)
        if msg_obj['id'] is None:
            return "", 404
        return jsonify(msg_obj)
    if request.method == 'DELETE':
        msg_id = MessageID
        if not msg_id:
            return "",404
        db2.del_msg(msg_id)
        return "", 200


@app.route('/message/<MessageID>/read/<UserID>', methods=['POST'])
def mark_read(MessageID, UserID):
    if request.method == 'POST':
        msg_id = MessageID
        user_id = UserID
        if not msg_id or not user_id:
            return "",404
        db2.mark_read(str(msg_id), str(user_id))
        return "", 200


@app.route('/message/unread/<UserID>', methods=['GET'])
def get_unread(UserID):
    user_id = UserID
    print('output')

    output = db2.get_unread(user_id)
    return jsonify(output)


if __name__ == '__main__':
    app.run(port=5000, debug=True)
