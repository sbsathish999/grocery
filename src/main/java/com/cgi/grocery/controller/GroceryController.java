package com.cgi.grocery.controller;

import com.cgi.grocery.modal.GroceryItem;
import com.cgi.grocery.modal.PriceData;
import com.cgi.grocery.modal.ItemPriceTrendingByYear;
import com.cgi.grocery.service.GroceryService;
import com.cgi.grocery.service.PriceService;
import com.cgi.grocery.service.PriceTrendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/grocery")
public class GroceryController {

    @Autowired
    PriceService priceService;

    @Autowired
    GroceryService groceryService;

    @Autowired
    PriceTrendingService priceTrendingService;
    @Value("${grocery.file.name}")
    private String fileName;

    @Value("${grocery.file.path}")
    private String filePath;

    @GetMapping(value = "/ping")
    public ResponseEntity ping(){
        return ResponseEntity.ok("Grocery Price App Tool is running.");
    }

    @GetMapping(value = "/max-sale-list")
    public ResponseEntity getGroceryMaxSaleList(){
        List<PriceData> result = priceService.getGroceryMaxSaleData(fileName, filePath);
        return result == null || result.isEmpty()
                ? ResponseEntity.unprocessableEntity().build()
                : ResponseEntity.ok(result);
    }

    @GetMapping(value = "/sale-list")
    public ResponseEntity getGrocerySaleDataByItem(@RequestParam String itemName){
        ItemPriceTrendingByYear result = priceTrendingService.getMaximumPriceDataByYear(itemName);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGrocerySaleDataByItem(){
        List<GroceryItem> result = groceryService.getAllGroceryItems();
        return result == null || result.isEmpty()
                ? ResponseEntity.unprocessableEntity().build()
                : ResponseEntity.ok(result);
    }
}
