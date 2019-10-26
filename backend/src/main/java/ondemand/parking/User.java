package ondemand.parking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class User {
    static Database db = ParkingOnDemandApplication.db;
    String uID;
    String psID;
    double rating;
    double raters;
    ArrayList<String> psToRate;


    User() {
    }

    User(String username, String password) {
        uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();
        rating = 0;
        raters = 0;
        psID = "";

        db.addUser(this);

        psToRate = new ArrayList<String>();
    }

    public void setPsID(String psID) {
        this.psID = psID;
    }

    //reserve a parking spot
    @GetMapping("/reserve")
    static ResponseEntity<String> reserve(@RequestParam String userName, @RequestParam String psID) {
        User user = db.getUser(userName);
        ParkingSpot spot = db.getParkingSpot(psID);
        spot.taken = true;
        user.psToRate.add(psID);

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    //rate function, used for someone that wants to rate the seller
    @GetMapping("/rate")
    static ResponseEntity<String> rate(@RequestParam String uID, @RequestParam String psID, @RequestParam double rating) {

        if (rating < 0 || rating > 5) {
            return new ResponseEntity<>("Rating must be between 0 to 5", HttpStatus.BAD_REQUEST);
        }

        User user = db.getUser(uID);
        ParkingSpot spot = db.getParkingSpot(psID);
        String targetID = spot.ownerId;
        User target = db.getUser(targetID);

        double newRating = (target.rating * target.raters + rating) / (target.raters + 1);
        target.rating = newRating;
        target.raters++;
        target.setPsID("");

        user.psToRate.remove(psID);
        db.removeParkingSpot(psID);

        return new ResponseEntity<String>(Double.toString(target.rating), HttpStatus.OK);
        //after reservation
        //return a my trips and person that rented to you
        //list a names of
    }


    // Signup function, creates a new user if it is a valid username
    @GetMapping("/signup")
    static ResponseEntity<String> signup(@RequestParam String username, @RequestParam String password) {
        String uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();

        if (!db.userExists(uID)) {
            db.addUser(new User(username, password));
            return new ResponseEntity<>(uID, HttpStatus.OK);
        }
        return new ResponseEntity<>("Username In Use", HttpStatus.BAD_REQUEST);
    }

    // Login Function, authenticates the user
    @GetMapping("/login")
    static ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();
        if (!db.userExists(uID)) {
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(uID, HttpStatus.OK);
    }

    // List a parking spot on the platform
    @GetMapping("/listSpot")
    static ResponseEntity<String> listSpot(@RequestParam String uid, @RequestParam double lon,
                                           @RequestParam double lat, @RequestParam long time,
                                           @RequestParam long duration, @RequestParam double meterRate) {
        // Check if user already has a parking spot listed
        User user = db.getUser(uid);
        if (!user.psID.isEmpty()) {
            return new ResponseEntity<>("Cannot list more than one spot at a time", HttpStatus.BAD_REQUEST);
        }
        String psID = UUID.randomUUID().toString();
        ParkingSpot spot = new ParkingSpot(psID, uid, lon, lat, time, duration, meterRate);
        db.addParkingSpot(spot);
        user.setPsID(psID);
        db.addSupplyRecord(new ChurnRecord(lon, lat, time));
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    // TODO: return JSON of valid parking spots
    @GetMapping("/view")
    static ResponseEntity<String> viewReservations(@RequestParam double lon, @RequestParam double lat,
                                                   @RequestParam long start, @RequestParam long end,
                                                   @RequestParam double radius, @RequestParam String sortMethod) {
        List<ParkingSpot> validSpots = new ArrayList<>();
        for (ParkingSpot ps : db.getParkingSpots()) {
            if (validParkingSpot(ps, lon, lat, radius, start, end)) {
                validSpots.add(ps);
            }
        }
        if (sortMethod.equals("closest")) {
            validSpots.sort((ps1, ps2) -> distance(ps1, lon, lat) < distance(ps2, lon, lat) ? -1 : 1);
        } else if (sortMethod.equals("meter rate")) {
            validSpots.sort((ps1, ps2) -> ps1.meterRate < ps2.meterRate ? -1 : 1);
        } else if (sortMethod.equals("price")) {
            validSpots.sort((ps1, ps2) -> ps1.price < ps2.price ? -1 : 1);
        }
        db.addDemandRecord(new ChurnRecord(lon, lat, start));
        return new ResponseEntity<>(validSpots.toString(), HttpStatus.OK);
    }

    //Returns in miles
    static double distance(ParkingSpot ps, double lon, double lat) {
        
        double r = 3958.8; //radius of earth
        double dLon = Math.toRadians(lon - ps.lon);
        double dLat = Math.toRadians(lat - ps.lat);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(ps.lat)) * Math.cos(Math.toRadians(lat)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = r * c;
        return d;


    }

    static boolean validParkingSpot(ParkingSpot ps, double lon, double lat, double radius, long start, long end) {
        return distance(ps, lon, lat) < radius
                && (start < ps.time + ps.duration && ps.time < end);
    }
}
