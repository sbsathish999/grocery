package com.cgi.grocery.service;

import com.cgi.grocery.modal.ItemPriceTrendingByYear;
import com.cgi.grocery.modal.PriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceTrendingServiceImpl implements PriceTrendingService{
    @Autowired
    PriceService priceService;
    @Override
    public ItemPriceTrendingByYear getMaximumPriceDataByYear(String itemName) {
        List<PriceData> priceByItems = priceService.getGrocerySaleDataByItem(itemName);
        Map<String, List<Float>> priceByDateMap = convertToPriceDateMap(priceByItems);
        Map<String, Float> maxPriceByDateMap = calculateMaximumPriceByYear(priceByDateMap);
        List<Float> prices = maxPriceByDateMap.values().stream().collect(Collectors.toList());
        List<String> years = maxPriceByDateMap.keySet().stream().collect(Collectors.toList());
        return priceByItems.isEmpty() ? new ItemPriceTrendingByYear() : new ItemPriceTrendingByYear(priceByItems.get(0).getItemName(), prices, years);
    }
    protected Map<String, Float> calculateMaximumPriceByYear(Map<String, List<Float>> priceByDateMap) {
        Map<String, Float> maxPriceByDateMap = new TreeMap<>();
        priceByDateMap
                .entrySet()
                .forEach( e-> maxPriceByDateMap.put(e.getKey()
                            , Float.valueOf(String.valueOf(e.getValue()
                                                            .stream()
                                                            .mapToDouble(a -> a)
                                                            .max()
                                                            .getAsDouble()))));
        return maxPriceByDateMap;
    }

    protected Map<String, List<Float>> convertToPriceDateMap(List<PriceData> priceByItems) {
        Map<String, List<Float>> priceByDateMap = new TreeMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        priceByItems.stream().forEach(pd -> {
            String year = dateFormat.format(pd.getDate());
            if(priceByDateMap.get(year) == null) {
                priceByDateMap.put(year, new ArrayList<>(Arrays.asList(pd.getPrice())));
            } else {
                List<Float> currentPrices = priceByDateMap.get(year);
                currentPrices.add(pd.getPrice());
                priceByDateMap.put(year, currentPrices);
            }
        });
        return priceByDateMap;
    }
}
