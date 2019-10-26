package ondemand.parking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class User {
    static Database db = ParkingOnDemandApplication.db;
    String uID;
    String psID;
    double rating;
    double raters;

    User() {}

    User(String username, String password) {
        uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();
        rating = 0;
        raters = 0;

        ParkingOnDemandApplication.db.addUser(this);
    }

    // Login function, creates a new user if necessary
    @GetMapping("/login")
    static ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();

        if (!db.userExists(uID)) {
            new User(username, password);
            return new ResponseEntity<>(uID, HttpStatus.OK);
        }
        return new ResponseEntity<>("Username In Use", HttpStatus.BAD_REQUEST);
    }
}
