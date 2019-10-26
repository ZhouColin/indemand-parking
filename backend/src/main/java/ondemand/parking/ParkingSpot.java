package ondemand.parking;

public class ParkingSpot {
    String psID;
    double[] location;
    long time;
    long duration;
    boolean taken;

    ParkingSpot(double[] location, long time, long duration) {
        this.location = location;
        this.time = time;
        this.duration = duration;

        ParkingOnDemandApplication.db.addParkingSpot(this);
    }
}
