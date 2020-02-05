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
        new_id = db2.store_message(msg)
        outdata = {'id' : new_id}
        print(outdata)
        return jsonify(outdata)

    elif request.method == 'GET':
        all_messages = db2.get_all_msg()
        print(all_messages)
        return jsonify(all_messages)


@app.route('/init_db')
def init_db():
    db2.init_db()
    return ""

@app.route('/message/<MessageID>', methods=['DELETE', 'GET'])
def get_message(MessageID):
    if request.method == 'GET':
        msg_obj = db2.get_msg(MessageID)
        print(msg_obj)
        return jsonify(msg_obj)
    if request.method == 'DELETE':
        msg_id = MessageID
        db2.del_msg(msg_id)
        return "", 200


@app.route('/messages/<MessageID>/read/<UserID>', methods=['POST'])
def mark_read(MessageID, UserID):



if __name__ == '__main__':
    app.run(port=5000,debug=True)
