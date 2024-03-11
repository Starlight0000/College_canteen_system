package com.project.dinedynamo.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    @Value("${twilio.trial_number}") String trialNumber;


    public SMSService(@Value("${twilio.account_sid}") String accountSid,
                      @Value("${twilio.auth_token}") String authToken) {
        Twilio.init(accountSid, authToken);
    }


    public void sendSMS(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(trialNumber), messageBody).create();
        System.out.println(message.getSid());}
}
