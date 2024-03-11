package com.project.dinedynamo.Service;

import com.project.dinedynamo.Entities.Items;
import com.project.dinedynamo.Repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemsService {

    //Repository based functions

    @Autowired
    ItemsRepository itemsrepository;

    public List<Items> getItemsByCategory(String category) {
        return itemsrepository.findByCategory(category);
    }

    public List<Items> getAllItems() {
        return itemsrepository.findAll();
    }

    public List<Items> getItemsByPrice(int price) {
        return itemsrepository.findByPrice(price);
    }

    public Items addItems(Items items) {
        return itemsrepository.save(items);
    }


    public Items getItemByName(String name) {
        return itemsrepository.findByName(name);
    }

}
