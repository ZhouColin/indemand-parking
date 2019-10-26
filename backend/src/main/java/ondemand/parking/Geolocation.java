package ondemand.parking;

public class Geolocation {

    // Returns distance in miles
    static double distance(ParkingSpot ps, double lon, double lat) {
        return distance(ps.lon, ps.lat, lon, lat);
    }

    static double distance(ChurnRecord cr, double lon, double lat) {
        return distance(cr.lon, cr.lat, lon, lat);
    }

    static double distance(double lon1, double lat1, double lon2, double lat2) {

        double r = 3958.8; //radius of earth
        double dLon = Math.toRadians(lon2 - lon1);
        double dLat = Math.toRadians(lat2 - lat1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;


    }
}
