package ondemand.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingOnDemandApplication {

    static Database db = new Database();

    public static void main(String[] args) {
        SpringApplication.run(ParkingOnDemandApplication.class, args);
    }

}
