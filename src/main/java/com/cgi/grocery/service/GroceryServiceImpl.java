package com.cgi.grocery.service;

import com.cgi.grocery.config.GrocerySaleData;
import com.cgi.grocery.modal.GroceryItem;
import com.cgi.grocery.modal.PriceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroceryServiceImpl implements GroceryService{

//    @Autowired
//    List<PriceData> priceData;

      @Autowired
      GrocerySaleData grocerySaleData;
    @Override
    public List<GroceryItem> getAllGroceryItems() {
        Map<String, GroceryItem> groceryItemMap = new TreeMap<>();
        grocerySaleData.read().stream().forEach(e -> groceryItemMap.put(e.getItemName(), new GroceryItem(e.getItemName())));
        return groceryItemMap.isEmpty()
                ? new ArrayList<>() : groceryItemMap
                                                .values()
                                                .stream()
                                                .collect(Collectors.toList());
    }
}
