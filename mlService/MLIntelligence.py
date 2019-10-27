from flask import Flask, request, jsonify

import pandas as pd, numpy as np
import geopandas as gpd
import folium
from folium.plugins import HeatMap
import os
import os.path


app = Flask(__name__)

@app.route('/data', methods=['GET'])
def getData():
	data = request.get_json()
	results = clustering(data)
	return jsonify(results)


# Do your ML stuff here
# Assumes that you parse the json data that we received from Java
# No need to store return values as a json file, this will be done by caller
def clustering(data, lat, lon, radius):

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

	def clusterHeatMap(coordinateList):

		weights = [1 for i in range(len(coordinateList))]
		coords = list(zip([c.lat for c in coordinateList], [c.lon for c in coordinateList], weights))
		#coords = pd.as_matrix(coords)

		outputMap = folium.Map(location=[lat, lon])
		hmap = HeatMap(coords, max_val = 1, radius = radius, blur = 15, min_opacity = .2)

		outputMap.add_child(hmap)

		os.chdir("..\\backend\\heatmaps")
		outputMap.save(str(lat)+str(lon) + ".html")



	clusterHeatMap(supply)


	'''
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






if __name__ == '__main__':
	app.run(debug=False)


