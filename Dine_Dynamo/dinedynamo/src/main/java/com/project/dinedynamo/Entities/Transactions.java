package com.project.dinedynamo.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Transactions")
public class Transactions {

    private String phoneNumber;
    private List<String> orders;
    private LocalDateTime datetime;
    private int total;

}
