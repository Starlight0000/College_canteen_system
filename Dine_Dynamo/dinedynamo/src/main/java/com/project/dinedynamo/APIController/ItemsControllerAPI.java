package com.project.dinedynamo.APIController;

import com.project.dinedynamo.Entities.Items;
import com.project.dinedynamo.Entities.SMS;
import com.project.dinedynamo.JavaEmailService;
import com.project.dinedynamo.Service.ItemsService;
import com.project.dinedynamo.Service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsControllerAPI {

    @Autowired
    ItemsService itemsservice;

    @Autowired
    JavaEmailService javaemailservice;

    @Autowired
    SMSService smsService;





    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam String email, @RequestParam String message) {
        javaemailservice.message(email, "DineDynamo", message);
        return ResponseEntity.ok("Email sent");
    }

    @PostMapping("/processSMS")
    public ResponseEntity<?> processSMS(@RequestBody SMS sms) {
        smsService.sendSMS(sms.getToPhoneNumber(), sms.getMessage());
        return ResponseEntity.ok("SMS sent");
    }



}



