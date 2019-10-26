from flask import Flask, request, jsonify
from sklearn.cluster import DBSCAN
import pandas as pd, numpy as np, matplotlib.pyplot as pyplot
from geopy.distance import great_circle
from shapely.geometry import MultiPoint


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

	database = pd.DataFrame(list(JSON.parse(data).items()))
	'''
		Contents of the database are ChurnRecord Class
		ChurnRecord Class:
			double lon;
	    	double lat;
	    	long time;
	'''

	supply = database[:0]
	demand = database[:1]

	def findCluster(coordinateList):

		coords = zip([c.lat for c in coordinateList], [c.lon for c in coordinateList])
		coords = pd.as_matrix(coords)
		
		
		#miles per radian
		mpr = 3963.20488348
		epsilon = 1.5 / mpr
		dbObj = DBSCAN(eps=epsilon, min_samples=1, algorithm='ball_tree', metric='haversine')
		db = dbObj.fit(np.radians(coords))
		cluster_labels = db.labels_
		num_clusters = len(set(cluster_labels))
		clusters = pd.Series([coords[cluster_labels == n] for n in range(num_clusters)])  #unsure about this line...seems like youre just taking the first n entries in coords?
		#print('Number of clusters: {}'.format(num_clusters))


		def getCenter(cluster):
	    	centroid = (MultiPoint(cluster).centroid.x, MultiPoint(cluster).centroid.y)
	    	centermost_point = min(cluster, key=lambda point: great_circle(point, centroid).m)
	    	return tuple(centermost_point)

		centermost_points = clusters.map(getCenter)

		return list(centermost_points)

	return [findCluster(supply), findCluster(demand)]

	'''
	lats, lons = zip(*centermost_points)
	rep_points = pd.DataFrame({'lon':lons, 'lat':lats})
	'''

	'''
	# choose k random indices in database to be centroids
	centroidIndices = [randint(0, demand.len() - 1) for i in range(k)]
	#List of each cluster's centroid object
	centroids = [database[centroidIndices[i]] for i in range(k)]
	#Maps centroid to its index number in centroids list (for convenience)
	#possible error where lists are too long for 'zip', shd check
	clusterIndex = dict(zip(centroids, [x for x in range(k)]))
	#List of elements in each cluster
	#ith cluster's centroid will be ith element in 'centroids'
	clusters = [[centroids] for i in range(k)]

	optimal = False

	while(!optimal):	
		#Assignment
		for churnRecord in demand:
			closestCentroid = min(centroids, key = lambda c: coordToDistance(churnRecord, c))
			clusters[clusterIndex[closestCentroid]].append(churnRecord)

		#Update
		updatedCentroids = [mean]


		#Check if optimal
		
	'''		






if __name__ == '__main__':
	app.run(debug=False)


