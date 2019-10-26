package ondemand.parking;

public class ParkingSpot {
    String psID;
    String ownerId;
    double[] location;
    double meterRate;
    long time;
    long duration;
    boolean taken;


    ParkingSpot(double[] location, long time, long duration, double m) {
        this.location = location;
        this.time = time;
        this.duration = duration;
        this.meterRate = m;

        ParkingOnDemandApplication.db.addParkingSpot(this);
    }
}
