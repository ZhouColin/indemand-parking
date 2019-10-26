package ondemand.parking;

import java.util.*;

// TODO: Change to directory format and support serializing to disk
public class Database {

    private HashMap<String, User> users;
    private HashMap<String, ParkingSpot> parkingSpots;
    private List<ChurnRecord> supply;
    private List<ChurnRecord> demand;

    Database() {
        users = new HashMap<>();
        parkingSpots = new HashMap<>();
        supply = new ArrayList<>();
        demand = new ArrayList<>();
    }

    boolean addUser(User u) {
        return users.put(u.uID, u) == null;
    }

    boolean addParkingSpot(ParkingSpot p) {
        return parkingSpots.put(p.psID, p) == null;
    }

    boolean userExists(String uID) {
        return users.containsKey(uID);
    }

    User getUser(String uId) {
        return users.get(uId);
    }

    ParkingSpot getParkingSpot(String psID) {
        return parkingSpots.get(psID);
    }

    boolean removeParkingSpot(String psID) {
        return parkingSpots.remove(psID) != null;
    }

    Collection<ParkingSpot> getParkingSpots() {
        return parkingSpots.values();
    }

    boolean addSupplyRecord(ChurnRecord record) {
        return supply.add(record);
    }

    boolean addDemandRecord(ChurnRecord record) {
        return demand.add(record);
    }

    void dumpUsers() {
        for (String id: users.keySet()) {
            System.out.println(id);
        }
    }
}

class ChurnRecord {
    double lon;
    double lat;
    long time;

    ChurnRecord(double lon, double lat, long time) {
        this.lon = lon;
        this.lat = lat;
        this.time = time;
    }
}
