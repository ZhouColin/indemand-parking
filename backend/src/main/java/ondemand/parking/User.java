package ondemand.parking;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.jsoup.Jsoup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class User {
    static Database db = ParkingOnDemandApplication.db;
    static StripeService stripeService = ParkingOnDemandApplication.stripeService;
    static final String twilioNumber = "+16464000794";
    String uID;
    String psID;
    double rating;
    double raters;
    ArrayList<String> psToRate;
    String firstName;
    String lastName;
    String phoneNumber;


    User() {}

    User(String username, String password, String firstName, String lastName, String phoneNumber) {
        uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();
        rating = 0;
        raters = 0;
        psID = "";
        firstName = firstName;
        lastName = lastName;
        phoneNumber = phoneNumber;
        psToRate = new ArrayList<String>();

        db.addUser(this);
    }

    public void setPsID(String psID) {
        this.psID = psID;
    }

    //reserve a parking spot
    @GetMapping("/reserve")
    static ResponseEntity<String> reserve(@RequestParam String uID, @RequestParam String psID,
                                          @RequestParam String stripeToken) {
        User user = db.getUser(uID);
        ParkingSpot spot = db.getParkingSpot(psID);

        if (user == null || spot == null) {
            return new ResponseEntity<>("Invalid", HttpStatus.NOT_FOUND);
        }
        spot.taken = true;
        user.psToRate.add(psID);

        stripeService.createCharge(stripeToken, spot.price);

        return new ResponseEntity<>(psID, HttpStatus.OK);
    }

    //rate function, used for someone that wants to rate the seller
    @GetMapping("/rate")
    static ResponseEntity<String> rate(@RequestParam String uID, @RequestParam String psID, @RequestParam double rating) {

        if (rating != 1 && rating != 0) {
            return new ResponseEntity<>("Rating not valid", HttpStatus.BAD_REQUEST);
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

        return new ResponseEntity<>(Double.toString(target.rating), HttpStatus.OK);
        //after reservation
        //return a my trips and person that rented to you
        //list a names of
    }


    // Signup function, creates a new user if it is a valid username
    @GetMapping("/signup")
    static ResponseEntity<String> signup(@RequestParam String username, @RequestParam String password,
                                         @RequestParam String firstName, @RequestParam String lastName,
                                         @RequestParam String phoneNumber) {
        String uID = UUID.nameUUIDFromBytes((username + password).getBytes()).toString();

        if (!db.userExists(uID)) {
            db.addUser(new User(username, password, firstName, lastName, phoneNumber));
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
    static ResponseEntity<String> listSpot(@RequestParam String uID, @RequestParam double lon,
                                           @RequestParam double lat, @RequestParam long time,
                                           @RequestParam long duration, @RequestParam double meterRate) {
        // Check if user already has a parking spot listed
        User user = db.getUser(uID);
        if (user == null) {
            return new ResponseEntity<>("Invalid", HttpStatus.NOT_FOUND);
        }
        if (!user.psID.isEmpty()) {
            return new ResponseEntity<>("Cannot list more than one spot at a time", HttpStatus.BAD_REQUEST);
        }
        String psID = UUID.randomUUID().toString();

        ParkingSpot spot = new ParkingSpot(psID, uID, lon, lat, time, duration, meterRate);
        spot.price = ParkingSpot.recommendPrice(lon, lat, time);
        db.addParkingSpot(spot);
        user.setPsID(psID);
        db.addSupplyRecord(new ChurnRecord(lon, lat, time));
        return new ResponseEntity<>(psID, HttpStatus.OK);
    }

    // TODO: return JSON of valid parking spots
    @GetMapping("/view")
    static ResponseEntity<String> viewReservations(@RequestParam double lon, @RequestParam double lat,
                                                   @RequestParam long start, @RequestParam long end,
                                                   @RequestParam double radius, @RequestParam String sortMethod) {
        List<ParkingSpot> validSpots = new ArrayList<>();
        for (ParkingSpot ps : db.getParkingSpots()) {
            if (ParkingSpot.validParkingSpot(ps, lon, lat, radius, start, end)) {
                validSpots.add(ps);
            }
        }
        if (sortMethod.equals("closest")) {
            validSpots.sort((ps1, ps2) -> Geolocation.distance(ps1, lon, lat) < Geolocation.distance(ps2, lon, lat) ? -1 : 1);
        } else if (sortMethod.equals("meterRate")) {
            validSpots.sort((ps1, ps2) -> ps1.meterRate < ps2.meterRate ? -1 : 1);
        } else if (sortMethod.equals("price")) {
            validSpots.sort((ps1, ps2) -> ps1.price < ps2.price ? -1 : 1);
        }
        db.addDemandRecord(new ChurnRecord(lon, lat, start));
        return new ResponseEntity<>(JSONHelper.jsonPSList(validSpots), HttpStatus.OK);
    }

    @GetMapping("/parkingClusters")
    static ResponseEntity<String> parkingClusters(@RequestParam double lon, @RequestParam double lat,
                                                  @RequestParam double radius) {
        String fileName = db.findClusters(lon, lat, radius);
        if (fileName == "") {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        File f = new File(fileName);
        String content = "";
        try {
            content = Jsoup.parse(f, "UTF-8").toString();
        } catch (IOException e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    // TODO: Change text message to use reverseGeoCoding to return street address to user
    static void textUsersToRate() {
        for (User u: db.getAllUsers()) {
            for (String psID: u.psToRate) {
                ParkingSpot ps = db.getParkingSpot(psID);
                Message message = Message.creator(new PhoneNumber(u.phoneNumber), new PhoneNumber(twilioNumber),
                        "Please use the OnDemandParking app to rate your latest adventure at" +
                                "Lon: " + ps.lon + " and lat: " + ps.lat).create();
            }
        }
    }

    @GetMapping("genTestSF")
    static void generateTestDataSF() {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            String username = Integer.toString(rand.nextInt());
            String password = Integer.toString(rand.nextInt());
            User u = new User(username, password, "John", "Doe", "+18005555555");
            String psID = UUID.randomUUID().toString();
            double lon = -122.477438 + (-122.408863 - -122.477438) * rand.nextDouble();
            double lat = 37.736980 + (37.791981 - 37.736980) * rand.nextDouble();
            ParkingSpot spot = new ParkingSpot(psID, u.uID, lon, lat, 25, 100, 10);
            spot.price = ParkingSpot.recommendPrice(lon, lat, 25);
            db.addParkingSpot(spot);
            u.setPsID(psID);
            db.addSupplyRecord(new ChurnRecord(lon, lat, 25));
        }
    }

    @GetMapping("genTestBerkeley")
    static void generateTestDataBerkeley() {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            String username = Integer.toString(rand.nextInt());
            String password = Integer.toString(rand.nextInt());
            User u = new User(username, password, "John", "Doe", "+18005555555");
            String psID = UUID.randomUUID().toString();
            double lon = -122.291944 + (-122.259127 - -122.291944) * rand.nextDouble();
            double lat = 37.855709 + (37.882149 - 37.855709) * rand.nextDouble();
            ParkingSpot spot = new ParkingSpot(psID, u.uID, lon, lat, 25, 100, 10);
            spot.price = ParkingSpot.recommendPrice(lon, lat, 25);
            db.addParkingSpot(spot);
            u.setPsID(psID);
            db.addSupplyRecord(new ChurnRecord(lon, lat, 25));
        }
    }
}
