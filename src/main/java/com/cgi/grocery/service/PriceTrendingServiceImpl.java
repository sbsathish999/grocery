package com.cgi.grocery.service;

import com.cgi.grocery.modal.ItemPriceTrendingByYear;
import com.cgi.grocery.modal.PriceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PriceTrendingServiceImpl implements PriceTrendingService{
    @Autowired
    PriceService priceService;

    @Autowired
    RestTemplate restTemplate;
    @Override
    public ItemPriceTrendingByYear getMaximumPriceDataByYear(String itemName) {
        List<PriceData> priceByItems = priceService.getGrocerySaleDataByItem(itemName);
        if(priceByItems != null && !priceByItems.isEmpty()) {
            Map<String, List<Float>> priceByDateMap = convertToPriceDateMap(priceByItems);
            Map<String, Float> maxPriceByDateMap = calculateMaximumPriceByYear(priceByDateMap);
            List<Float> prices = maxPriceByDateMap.values().stream().collect(Collectors.toList());
            List<String> years = maxPriceByDateMap.keySet().stream().collect(Collectors.toList());
            return new ItemPriceTrendingByYear(itemName, prices, years);
        }
        return null;
    }

    @Override
    public ItemPriceTrendingByYear getMaximumPriceDataByYearAPI(String itemName) {
        List<PriceData> priceByItems = priceService.getGrocerySaleDataByItem(itemName);
        if(priceByItems != null && !priceByItems.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<PriceData>> httpEntity = new HttpEntity<>(priceByItems, headers);
            log.info("httpsentity" + httpEntity);
            ItemPriceTrendingByYear response =  restTemplate.postForObject("http://price-trending-service/grocery/sale-list?itemName="+ itemName
                                                                        , httpEntity, ItemPriceTrendingByYear.class);
            return response;
        }
        return null;
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
