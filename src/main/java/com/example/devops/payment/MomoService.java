package com.example.devops.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MomoService {

    private final MomoConfig momoConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> createPayment(String orderInfo, long amount) throws Exception {
        String requestId = UUID.randomUUID().toString();
        String orderId = UUID.randomUUID().toString();
        String requestType = "captureWallet";
        String extraData = "";

        // Raw signature string
        String rawSignature = "accessKey=" + momoConfig.getAccessKey()
                + "&amount=" + amount
                + "&extraData=" + extraData
                + "&ipnUrl=" + momoConfig.getIpnUrl()
                + "&orderId=" + orderId
                + "&orderInfo=" + orderInfo
                + "&partnerCode=" + momoConfig.getPartnerCode()
                + "&redirectUrl=" + momoConfig.getRedirectUrl()
                + "&requestId=" + requestId
                + "&requestType=" + requestType;

        String signature = hmacSHA256(rawSignature, momoConfig.getSecretKey());

        // Build request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("partnerCode", momoConfig.getPartnerCode());
        requestBody.put("requestId", requestId);
        requestBody.put("amount", amount);
        requestBody.put("orderId", orderId);
        requestBody.put("orderInfo", orderInfo);
        requestBody.put("redirectUrl", momoConfig.getRedirectUrl());
        requestBody.put("ipnUrl", momoConfig.getIpnUrl());
        requestBody.put("lang", "vi");
        requestBody.put("requestType", requestType);
        requestBody.put("autoCapture", true);
        requestBody.put("extraData", extraData);
        requestBody.put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Send request to MoMo
        return restTemplate.postForObject(momoConfig.getEndpoint(), entity, Map.class);
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        byte[] bytes = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
