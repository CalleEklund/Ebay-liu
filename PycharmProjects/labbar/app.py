from flask import Flask, jsonify
import uuid
app = Flask(__name__)


# global dict

messages = [{'id': '220f7259-beb1-4012-aa59-6e787a0cd581', 'text': 'demo0', 'readBy': []}, {'id': 'a0d84018-d718-4715-a645-ff375d4b3a13', 'text': 'demo1', 'readBy': []}, {'id': 'afe83630-4cbf-4000-baef-38b13145bf65', 'text': 'demo2', 'readBy': []}, {'id': '01d6d904-dc32-45fb-9c36-f6c8719cd690', 'text': 'demo3', 'readBy': []}, {'id': 'a432a82a-6c4b-4c42-b9e0-6713e52b81cb', 'text': 'demo4', 'readBy': []}, {'id': 'ef9c846a-3477-4506-8f5c-a7200d5fab71', 'text': 'demo5', 'readBy': []}, {'id': '2298b7e7-e4e7-48b6-bb13-ea6260d3cfe4', 'text': 'demo6', 'readBy': []}]

"""
def create_msg():
    global messages
    for i in range(7):
        messages.append({'id':uuid.uuid4(),'text':'demo'+str(i),'readBy':[]})
create_msg()
"""

@app.route('/')
def hello_world():
    return 'Hellus eVerYbodieS!'


@app.route('/messages/',methods=['POST'])
def message():
    msg = "demo0"
    outdata = get_id_msg(None,msg)
    outdata = {'id':outdata['id']}
    return jsonify(outdata)

@app.route('/messages/<MessageID>',methods=['GET'])
def get_message(MessageID):
    id = MessageID
    out = get_id_msg(id,None)
    print(out)
    return jsonify(out)

def get_id_msg(id=None,msg=None):
    for elem in messages:
        if id in elem.values() or msg in elem.values():
            return elem

    return None


if __name__ == '__main__':
    app.run(debug=True)
