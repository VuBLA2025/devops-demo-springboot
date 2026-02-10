package com.example.devops.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow Frontend to call
public class PaymentController {

    private final MomoService momoService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestParam String orderInfo, @RequestParam long amount) {
        try {
            Map<String, Object> response = momoService.createPayment(orderInfo, amount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/ipn")
    public ResponseEntity<?> ipnCallback(@RequestBody Map<String, Object> payload) {
        // Handle IPN from MoMo (Update Order Status)
        System.out.println("IPN Received: " + payload);
        return ResponseEntity.noContent().build();
    }
}
