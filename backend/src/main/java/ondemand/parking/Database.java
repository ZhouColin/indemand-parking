package ondemand.parking;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

// TODO: Change to directory format and support serializing to disk
public class Database {

    private HashMap<String, User> users;
    private HashMap<String, ParkingSpot> parkingSpots;

    Database() {
        users = new HashMap<>();
        parkingSpots = new HashMap<>();
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
}
