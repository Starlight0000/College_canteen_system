package com.project.dinedynamo.Service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {

    @Autowired
    SMSService smsService;

    private Map<String, String> otpStore = new HashMap<>();

    public void generateAndSendOTP(String phoneNumber) {
        phoneNumber = "+91" + phoneNumber;

        // Generate a random OTP
        String otp = generateRandomOTP();

        // Store the OTP in the otpStore map
        otpStore.put(phoneNumber, otp);

        // Send the OTP to the user's phone number
        sendOTP(phoneNumber, otp);
    }

    public String generateRandomOTP() {
        // Generate a random numeric OTP of 6 digits
        return RandomStringUtils.randomNumeric(6);
    }

    private void sendOTP(String phoneNumber, String otp) {
        System.out.println("otp is " + otp);
        // Construct an OTP message
        String message = "Your OTP is: " + otp;

        // Send the message to the user's phone number via SMS
        smsService.sendSMS(phoneNumber, message);
    }

    public boolean verifyOTP(String phoneNumber, String otp) {
        // Retrieve the stored OTP associated with the user's phone number
        String storedOTP = retrieveStoredOTP(phoneNumber);

        // Compare the entered OTP with the stored OTP
        boolean verificationResult = storedOTP != null && storedOTP.equals(otp);

        // Print a verification message
        System.out.println("Verification result for phone number " + phoneNumber + ": " + verificationResult);

        return verificationResult;
    }

    private String retrieveStoredOTP(String phoneNumber) {
        // Retrieve the stored OTP from the map
        phoneNumber = "+91" + phoneNumber;
        System.out.println("Retrieving OTP for " + phoneNumber);
        String storedOTP = otpStore.get(phoneNumber);

        // Print the stored OTP
        System.out.println("Stored OTP is: " + storedOTP);

        return storedOTP;
    }
}
