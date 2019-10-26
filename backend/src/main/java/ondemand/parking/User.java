package ondemand.parking;

import java.util.UUID;

public class User {
    static Database db = ParkingOnDemandApplication.db;
    String uID;
    String psID;
    double rating;
    double raters;

    User(String username, String password) {
        uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();
        rating = 0;
        raters = 0;

        ParkingOnDemandApplication.db.addUser(this);
    }

    // Login function, creates a new user if necessary
    static String getUserID(String username, String password) {
        String uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();

        if (!db.userExists(uID)) {
            new User(username, password);
        }
        return uID;
    }
}
