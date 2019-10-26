package ondemand.parking;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

// TODO: Change to directory format and support serializing to disk
public class Database {

    private HashMap<String, User> users;
    private HashMap<String, ParkingSpot> parkingSpots;
    private ArrayList<ChurnRecord> supply;
    private ArrayList<ChurnRecord> demand;

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

    // TODO: Parse the results and update corresponding variables
    void getMLResults() {
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        try {
            URL url = new URL("http://127.0.0.1:5000/data");
            ArrayList<ArrayList<ChurnRecord>> data = new ArrayList<ArrayList<ChurnRecord>>();
            data.add(supply);
            data.add(demand);
            String json = new Gson().toJson(data);
            byte[] postData = json.getBytes(StandardCharsets.UTF_8);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
            os = new DataOutputStream(conn.getOutputStream());
            os.write(postData);
            os.flush();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String response = "";
            for (String line; (line = br.readLine()) != null; response += line);
            conn.disconnect();
        } catch (IOException ignored) {
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
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
