from flask import Flask

app = Flask(__name__)


@app.route('/')
def start_page():
    return "test för projekt"


if __name__ == "__main__":
    app.run(port=5000, debug=True)
