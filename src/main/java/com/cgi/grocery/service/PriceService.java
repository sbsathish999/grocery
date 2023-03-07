package com.cgi.grocery.service;

import com.cgi.grocery.modal.PriceData;

import java.util.List;

public interface PriceService {
    List<PriceData> getGroceryMaxSaleData(String fileName, String filePath);
    List<PriceData> getGrocerySaleDataByItem(String itemName);

}
