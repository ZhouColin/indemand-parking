from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/data', methods=['GET'])
def getData():
	data = request.get_json()
	results = clustering(data)
	return jsonify(results)


# Do your ML stuff here
# Assumes that you parse the json data that we received from Java
# No need to store return values as a json file, this will be done by caller
def clustering(data):



	return results


if __name__ == '__main__':
	app.run(debug=False)


