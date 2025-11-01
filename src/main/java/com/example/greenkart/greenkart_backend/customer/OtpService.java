package com.example.greenkart.greenkart_backend.customer;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private Map<String, OtpData> otpStorage = new HashMap<>();
    private static final int OTP_EXPIRY_MINUTES = 5;

    public String generateOtp(String contact) {
        String otp = String.format("%05d", new Random().nextInt(99999));
        otpStorage.put(contact, new OtpData(otp, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES)));
        sendSms(contact, otp);
        return otp;
    }

    private void sendSms(String contact, String otp) {
        System.out.println("Sending OTP " + otp + " to contact " + contact);
        // integrate Twilio or Firebase Messaging here later
    }

    public boolean verifyOtp(String contact, String enteredOtp) {
        OtpData data = otpStorage.get(contact);
        if (data == null) return false;
        if (LocalDateTime.now().isAfter(data.expiryTime())) return false;
        return data.otp().equals(enteredOtp);
    }

    private record OtpData(String otp, LocalDateTime expiryTime) {}
}
