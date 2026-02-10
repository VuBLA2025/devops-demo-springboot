package com.example.devops.payment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "momo")
public class MomoConfig {
    private String partnerCode = "MOMO"; // Sandbox default
    private String accessKey = "F8BGR64zLQZTgLvJ"; // Sandbox default placeholder
    private String secretKey = "at67qH6mk8w5Y1nAyMoYKMWACiAyGxs"; // Sandbox default placeholder
    private String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
    private String redirectUrl = "http://localhost:3000/return"; // Frontend return URL
    private String ipnUrl = "https://webhook.site/b3088a6a-2d17-4f8d-a70f-5201w4735289"; // Replace with your
                                                                                         // Ngrok/Server IPN
}
