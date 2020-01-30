from flask import Flask, jsonify, request
import db2


app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'hellu erbadies! welcums, to my cribs.'


@app.route('/messages/', methods=['POST', 'GET'])
def message():
    if request.method == 'POST':
        msg = request.json['msg']
        db2.store_message(msg)
        return msg
    elif request.method == 'GET':
        all_messages = db2.get_all_messages()
        print(all_messages)
        return jsonify(all_messages)


@app.route('/init_db')
def init_db():
    print("yaYEET")
    db2.init_db()
    return ""


if __name__ == '__main__':
    app.run(debug=True)
