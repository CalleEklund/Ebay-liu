from flask import Flask

app = Flask(__name__)

@app.route('/')
def test_page():
    return "test för projekt"


if __name__ == "__main__":
    app.run(port=5000, debug=True)
