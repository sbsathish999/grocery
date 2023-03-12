package com.cgi.grocery.service;

import com.cgi.grocery.modal.ItemPriceTrendingByYear;

import java.util.List;

public interface PriceTrendingService {
    ItemPriceTrendingByYear getMaximumPriceDataByYear(String itemName);
}
