package ondemand.parking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class User {
    static Database db = ParkingOnDemandApplication.db;
    String uID;
    String psID;
    double rating;
    double raters;
    ArrayList<String> psToRate;


    User() {}

    User(String username, String password) {
        uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();
        rating = 0;
        raters = 0;

        ParkingOnDemandApplication.db.addUser(this);

        psToRate = new ArrayList<String>();
    }

    //reserve a parking spot
    static ResponseEntity<String> reserve(@RequestParam String userName, @RequestParam String psID) {
        ParkingSpot spot = db.getParkingSpot(psID);
        User user = db.getUser(userName);
        spot.taken = true;
        user.psToRate.add(psID);

        return new ResponseEntity<String>(HttpStatus.OK);
    }
    //rate function, used for someone that wants to rate the seller
    @GetMapping("/rate")
    static ResponseEntity<String> rate(@RequestParam String uID, @RequestParam String psID, @RequestParam double rating) {

        if(rating < 0 || rating > 5) {
            return new ResponseEntity<>("Rating must be between 0 to 5", HttpStatus.CONFLICT);
        }

        User user = db.getUser(uID);
        ParkingSpot spot = db.getParkingSpot(psID);
        String targetID = spot.ownerId;
        User target = db.getUser(targetID);

        double newRating = (target.rating * target.raters + rating)/(target.raters + 1);
        target.rating = newRating;
        target.raters++;

        user.psToRate.remove(psID);

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
            new User(username, password);
            return new ResponseEntity<>(uID, HttpStatus.OK);
        }
        return new ResponseEntity<>("Username In Use", HttpStatus.BAD_REQUEST);
    }

    // Login Function, authenticates the user
    @GetMapping("/login")
    static ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();
        if (db.userExists(uID)) {
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(uID, HttpStatus.OK);
    }
}
