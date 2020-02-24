from flask import Flask, jsonify, request
from flask_jwt_extended import jwt_required

app = Flask(__name__)
import dblab2 as db2


@app.route('/')
def hello_world():
    return 'hellu erbadies! welcums, to my cribs.'


@app.route('/message', methods=['POST'])
@jwt_required
def message():
    if request.method == 'POST':
        msg = request.json['message']
        if len(msg) > 140:
            return "", 400
        new_id = db2.store_message(msg)
        outdata = {'id': new_id}
        return jsonify(outdata)

@app.route('/message', methods=['GET'])
def get_all_messages():
    all_messages = db2.get_all_msg()
    return jsonify(all_messages)

@app.route('/init_db')
def init_db():
    db2.init_db()
    return ""


@app.route('/message/<MessageID>', methods=['GET'])
def get_message(MessageID):
    msg_obj = db2.get_msg(str(MessageID))
    # print(msg_obj)
    if msg_obj['id'] is None:
        print('test')
        return "", 404
    return jsonify(msg_obj)


@app.route('/message/<MessageID>', methods=['DELETE'])
@jwt_required
def delete_message(MessageID):
    msg_id = MessageID
    if not msg_id:
        return "", 404
    db2.del_msg(msg_id)
    return "", 200

@app.route('/message/<MessageID>/read/<username>', methods=['GET'])
@jwt_required
def mark_read(MessageID, username, password):
    msg_id = MessageID
    if not msg_id or not username:
        return "", 404
    db2.mark_read(str(msg_id), str(username), str(password))
    return "", 200


@app.route('/message/unread/<UserID>', methods=['GET'])
@jwt_required
def get_unread(UserID):
    user_id = UserID
    output = db2.get_unread(user_id)
    print(output)
    return output



if __name__ == '__main__':
    app.run(port=5000, debug=True)
