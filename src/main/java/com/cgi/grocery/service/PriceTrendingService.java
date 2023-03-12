package com.cgi.grocery.service;

import com.cgi.grocery.modal.ItemPriceTrendingByYear;

public interface PriceTrendingService {
    ItemPriceTrendingByYear getMaximumPriceDataByYear(String itemName);
}
