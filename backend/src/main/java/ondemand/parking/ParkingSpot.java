package ondemand.parking;

public class ParkingSpot {
    String psID;
    String ownerId;
    double[] location;
    double meterRate;
    long time;
    long duration;
    boolean taken;


    ParkingSpot(String psID, double[] location, long time, long duration, double meterPrice) {
        this.psID = psID;
        this.location = location;
        this.time = time;
        this.duration = duration;
        this.meterRate = meterPrice;

        ParkingOnDemandApplication.db.addParkingSpot(this);
    }
}
