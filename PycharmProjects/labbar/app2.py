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
        print(msg)
        new_id = db2.store_message(msg)
        return jsonify({'id' : new_id})

    elif request.method == 'GET':
        all_messages = db2.get_all_msg()
        print(all_messages)
        return jsonify(all_messages)


@app.route('/init_db')
def init_db():
    db2.init_db()
    return ""

@app.route('/delete/<MessageId>')
def delete_msg(MessageId):
    msg_id = MessageId
    db2.del_msg(msg_id)
    return "",200



if __name__ == '__main__':
    app.run(port=5000,debug=True)
