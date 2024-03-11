package com.project.dinedynamo.APIController;

import com.project.dinedynamo.Entities.Items;
import com.project.dinedynamo.Entities.User;
import com.project.dinedynamo.JavaEmailService;
import com.project.dinedynamo.Service.ItemsService;
import com.project.dinedynamo.Service.OTPService;
import com.project.dinedynamo.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserControllerAPI {

    @Autowired
    UserService userService;

    @Autowired
    OTPService otpService;

    @Autowired
    ItemsService itemsService;

    @Autowired
    JavaEmailService javaemailservice;

    @PostMapping("/createUser")
    public ModelAndView createUser(
            @RequestParam("name") String name,
            @RequestParam("rollNumber") String rollNumber,
            @RequestParam("phoneNumber") String phoneNumber
    ) {
        System.out.println("Creating user with name: " + name + ", roll number: " + rollNumber + ", and phone number: " + phoneNumber);

        userService.addUser(name, rollNumber, phoneNumber);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/checkPhoneNumber")
    public ResponseEntity<Map<String, Boolean>> checkPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        boolean exists = userService.checkIfPhoneNumberExists(phoneNumber);
        if (exists) {
            // If the phone number exists, generate and send the OTP
            otpService.generateAndSendOTP(phoneNumber);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOTP")
    public ModelAndView verifyOTP(
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("otp") String userOTP,
            HttpServletRequest request
    ) {
        if (otpService.verifyOTP(phoneNumber, userOTP)) {
            System.out.println("OTP verified successfully");

            // Start a session
            HttpSession session = request.getSession();
            session.setAttribute("phoneNumber", phoneNumber);

            // Redirect to the dashboard page in a different controller

        } else {
            System.out.println("Invalid OTP");
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/dashboard");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Perform any necessary session cleanup
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Redirect to the login page or another appropriate page
        return "redirect:/login";
    }
    @PostMapping("/login")
    public ModelAndView login()
    {
        return new ModelAndView("redirect:/login");
    }
    @PostMapping("/signup")
    public ModelAndView signup()
    {
        return new ModelAndView("redirect:/signup");
    }


    @PostMapping("/sendEmail")
    public ModelAndView sendEmail(HttpSession session) {
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        User user = userService.getUserByPhoneNumber(phoneNumber);
        String email = user.getRollNumber()+ "@klh.edu.in";
        String message = "Hey " + user.getName() + ",\n\nYour Order Summary:\n\n";

        List<String> cart = user.getCart();
        double total = 0.0;

        for (int i = 0; i < cart.size(); i++) {
            String item = cart.get(i);
            // Fetch the item details from the "items" collection based on item ID
            Items itemDetails = itemsService.getItemByName(item);

            if (itemDetails != null) {
                message += (i + 1) + ". " + itemDetails.getName() + " - $" + itemDetails.getPrice() + "\n";
                total += itemDetails.getPrice();
            }
        }
        message += "\nTotal: $" + total;
        String subject = "DineDynamo Order Summary";
        javaemailservice.message(email, subject, message);

        return new ModelAndView("redirect:/success");
    }

    @PostMapping("/addItemToCart")
    public ModelAndView addItemToCart(
            @RequestParam("name") String itemname,
            HttpSession session
    ) {
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        userService.addToCart(phoneNumber,itemname);
        return new ModelAndView("redirect:/dashboard");
    }


}


