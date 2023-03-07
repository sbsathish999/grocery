package com.cgi.grocery.service;

import com.cgi.grocery.modal.PriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService{
    @Autowired
    List<PriceData> fileData;

    @Override
    public List<PriceData> getGroceryMaxSaleData(String fileName, String filePath) {
        try {
            List<PriceData> priceDataList = fileData;
            List<PriceData> maxSaleList = filterOnlyMaximumSalesSortedByItemName(priceDataList);
            return maxSaleList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PriceData> getGrocerySaleDataByItem(String itemName) {
        try {
            List<PriceData> priceDataList = fileData;
            List<PriceData> specificItemList = priceDataList
                                                    .stream()
                                                    .filter(priceData -> priceData.getItemName()
                                                            .equalsIgnoreCase(itemName))
                                                    .sorted((p1, p2) -> p1.getDate().compareTo(p2.getDate()))
                                                    .collect(Collectors.toList());
            return specificItemList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected List<PriceData> filterOnlyMaximumSalesSortedByItemName(List<PriceData> priceDataList) {
        TreeMap<String, PriceData> maxMap = new TreeMap<>();
        priceDataList.stream().forEach( newData -> {
            if(newData.getItemName() != null && !newData.getItemName().isEmpty()
                    && newData.getPrice() != null && newData.getDate() != null) {
                PriceData oldData = maxMap.get(newData.getItemName());
                if(oldData == null
                    || newData.getPrice().compareTo(oldData.getPrice()) > 0) {
                    maxMap.put(newData.getItemName(), newData);
                }
            }
        });
        return maxMap.values().stream().toList();
    }
}
