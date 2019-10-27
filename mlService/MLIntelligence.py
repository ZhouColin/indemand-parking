from flask import Flask, request, jsonify, make_response
import logging
import pandas as pd, numpy as np
import random
#import geopandas as gpd
import folium
from folium.plugins import HeatMap
import os
import os.path



app = Flask(__name__)

@app.route('/data', methods=['POST'])
def getData():
	jsonData = request.get_json()
	logging.warning(jsonData)

	''' Supply and demand are a list of dictionaries, each dictionary contains the following:
		{'lon': LON_VALUE, 'lat': LAT_VALUE, 'time': TIME_VALUE}
	'''
	supply = jsonData['data'][0]
	demand = jsonData['data'][1]
	lon = jsonData['lon']
	lat = jsonData['lat']
	radius = jsonData['radius']
	# Modify this as needed for clustering
	fileName = clustering(supply, demand, lon, lat, radius)
	return fileName


def clustering(supply, demand, lon, lat, radius):

	def clusterHeatMap(coordinateList):

		weights = [1 for i in range(len(coordinateList))]

		coords = list(zip([c['lat'] for c in coordinateList], [c['lon'] for c in coordinateList], weights))
		outputMap = folium.Map(location=[lat, lon])
		hmap = HeatMap(coords, max_value = 1, min_opacity = .5)

		outputMap.add_child(hmap)

		if not os.path.exists("../backend/heatmaps"):
			os.mkdir("../backend/heatmaps")

		mapName = "../backend/heatmaps/lat" + str(lat) + "lon" + str(lon) + ".html"
		outputMap.save(mapName)
		return mapName


	return clusterHeatMap(supply)

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

#x = [[random.uniform(37.50, 38), random.uniform(-122.3, -122.2)] for i in range(100)]

#clusterHeatMap(x, 37.873229, -122.273590, 1)

#37.895908, 37.856463

#-122.290828, -122.256491


if __name__ == '__main__':
	app.run(debug=True)

