package com.project.dinedynamo.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class User {

    @Id
    private String id;

    private String name;
    private String rollNumber;
    private String phoneNumber;
    private List<String> cart;

    private boolean isAdmin;

    public User(String name, String rollNumber, String phoneNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.phoneNumber = phoneNumber;
        this.cart = new ArrayList<>();
        this.isAdmin = false;
    }

}
