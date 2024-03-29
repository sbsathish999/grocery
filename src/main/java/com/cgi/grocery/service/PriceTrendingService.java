package com.cgi.grocery.service;

import com.cgi.grocery.modal.ItemPriceTrendingByYear;
import com.cgi.grocery.modal.PriceData;

import java.util.List;

public interface PriceTrendingService {
    ItemPriceTrendingByYear getMaximumPriceDataByYear(String itemName);
    ItemPriceTrendingByYear getMaximumPriceDataByYearAPI(String itemName);
}
