package com.cgi.grocery.service;

import com.cgi.grocery.modal.PriceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroceryServiceImpl implements GroceryService{

    @Autowired
    List<PriceData> priceData;

    @Override
    public Set<String> getAllGroceryItems() {
        return priceData == null ? null : priceData
                                                .stream()
                                                .map(i -> i.getItemName())
                                                .sorted()
                                                .collect(Collectors.toCollection(TreeSet :: new));
    }
}
