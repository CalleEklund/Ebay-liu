from flask import Flask, jsonify, request


app = Flask(__name__)
import db2

@app.route('/')
def hello_world():
    return 'hellu erbadies! welcums, to my cribs.'


@app.route('/messages/', methods=['POST', 'GET'])
def message():
    if request.method == 'POST':
        msg = request.json['message']
        print(msg)
        db2.store_message(msg)
        #msg_id = db2.query.filter(text=msg).first()
        #print(msg_id)
        return msg
    elif request.method == 'GET':
        all_messages = db2.get_all_msg()
        print(all_messages)
        return jsonify(all_messages)


@app.route('/init_db')
def init_db():
    print("yaYEET")
    db2.init_db()
    return ""

@app.route('/get_all')
def get_all():
    return jsonify(db2.get_all_msg())

if __name__ == '__main__':
    app.run(port=5000,debug=True)
