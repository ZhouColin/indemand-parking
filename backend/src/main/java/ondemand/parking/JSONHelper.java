package ondemand.parking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JSONHelper {

    static String jsonPSList(List<ParkingSpot> psList) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"parkingSpots\": [");
        if (!psList.isEmpty()) {
            for (ParkingSpot ps : psList) {
                if (!ps.taken) {
                    sb.append(toJSON(ps));
                    sb.append(", ");
                }
            }
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]}");
        return sb.toString();
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
