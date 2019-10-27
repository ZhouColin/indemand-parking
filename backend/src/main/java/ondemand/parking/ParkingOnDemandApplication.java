package ondemand.parking;

import com.twilio.Twilio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingOnDemandApplication {

    static Database db = new Database();
    static StripeService stripeService = new StripeService();
    // TODO: Read these values from persistent store
    static final String TWILIO_ACCOUNT_SID = "AC02c554adcf9194a5c53cc14124c68420";
    static final String TWILIO_AUTH_TOKEN = "022cf9a526e01d2a6a3c79e81bf0aa8a";


    public static void main(String[] args) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        SpringApplication.run(ParkingOnDemandApplication.class, args);
    }

}
