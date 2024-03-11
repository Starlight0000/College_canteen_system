package com.project.dinedynamo.Service;

import com.project.dinedynamo.Entities.Items;
import com.project.dinedynamo.Entities.User;
import com.project.dinedynamo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userrepository;

    @Autowired
    OTPService otpService;

    @Autowired
    ItemsService itemsService;

    public void addUser(String name, String rollNumber, String phoneNumber ) {
        User user = new User(name, rollNumber, phoneNumber);
        userrepository.save(user);
    }

    public User getUserDetailsByName(String name) {
        return userrepository.findByName(name);
    }

    public List<User> getAllUsers() {
        return userrepository.findAll();
    }

    public boolean checkIfPhoneNumberExists(String phoneNumber) {
        return userrepository.existsByPhoneNumber(phoneNumber);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userrepository.findByPhoneNumber(phoneNumber);
    }

    public void addToCart(String phoneNumber, String itemName) {
        // Retrieve the user from the database
        User user = userrepository.findByPhoneNumber(phoneNumber);

        // Add the item to the user's cart
        user.getCart().add(itemName);

        // Save the updated user document in the database
        userrepository.save(user);

        // Print the updated count of items in the cart
        System.out.println("No of items in the cart is " + user.getCart().size());



        int total = 0;
        for (String item : user.getCart()) {
            Items items = itemsService.getItemByName(item);
            System.out.println("User has " + item + " in the cart "+ "price is " + items.getPrice());
            total+=items.getPrice();
            System.out.println("Total is " + total);


        }



    }


    public List<Items> getCartItems(String phonenumber) {
        User user = getUserByPhoneNumber(phonenumber);
        System.out.println(user.getCart());
        List<Items> cartitems = new ArrayList<>();
        List<String> cartitemnames = user.getCart();
        for(String item : cartitemnames)
        {
           Items retrieveditem = itemsService.getItemByName(item);
           cartitems.add(retrieveditem);

        }
        return cartitems;



    }
}

