package ondemand.parking;

public class ParkingSpot {
    String psID;
    String ownerId;
    double lon;
    double lat;
    double meterRate;
    long time;
    long duration;
    boolean taken;


    ParkingSpot(String psID, double lon, double lat, long time, long duration, double meterPrice) {
        this.psID = psID;
        this.lon = lon;
        this.lat = lat;
        this.time = time;
        this.duration = duration;
        this.meterRate = meterPrice;

        ParkingOnDemandApplication.db.addParkingSpot(this);
    }
}