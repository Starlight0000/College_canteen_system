package com.project.dinedynamo.Repository;

import com.project.dinedynamo.Entities.Items;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
public interface ItemsRepository extends MongoRepository<Items, String> {

    List<Items> findByCategory(String category);


    List<Items> findByPrice(int price);

    Items findByName(String name);
}
