package ondemand.parking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParkingSpot {
    String psID;
    String ownerId;
    double lon;
    double lat;
    double meterRate;
    double price;
    long time;
    long duration;
    boolean taken;


    ParkingSpot(String psID, String ownerId, double lon, double lat, long time, long duration, double meterRate) {
        this.psID = psID;
        this.ownerId = ownerId;
        this.lon = lon;
        this.lat = lat;
        this.meterRate = meterRate;
        this.price = price;
        this.time = time;
        this.duration = duration;
        this.taken = false;
        this.price = recommendPrice(lon, lat, time);

        ParkingOnDemandApplication.db.addParkingSpot(this);
    }

    // TODO: ML price recommendation
    static double recommendPrice(double lon, double lat, long time) {
        return 0;
    }

    static String toJSON(ParkingSpot ps) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        try {
            return mapper.writeValueAsString(ps);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}