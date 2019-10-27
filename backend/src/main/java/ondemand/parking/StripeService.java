package ondemand.parking;

import com.stripe.Stripe;
import com.stripe.model.Charge;

import java.util.HashMap;
import java.util.Map;

public class StripeService {
    // TODO: Add credentials to persistent store
    private static final String API_SECRET_KEY = "sk_test_gQ611rVwFxu3zfPeYG8XsdZ600ZJIk5p5b";

    public StripeService() {}

    public String createCharge(String token, double amount) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "usd");
            chargeParams.put("source", token);
            Charge charge = Charge.create(chargeParams);
            id = charge.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
