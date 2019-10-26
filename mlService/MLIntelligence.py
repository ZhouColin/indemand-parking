from flask import Flask, request, jsonify, make_response
import logging

app = Flask(__name__)

@app.route('/data', methods=['GET', 'POST'])
def getData():
	data = request.get_json()
	logging.warning(data)
	results = jsonify(clustering(data))
	logging.warning(results)
	return make_response(results, 200)


# Do your ML stuff here
# Assumes that you parse the json data that we received from Java
# No need to store return values as a json file, this will be done by caller
def clustering(data):
	results = [[1, 2, 3], [4, 5, 6]]
	return results


if __name__ == '__main__':
	app.run(debug=True)


