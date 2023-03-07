package com.cgi.grocery.controller;

import com.cgi.grocery.modal.PriceData;
import com.cgi.grocery.service.GroceryService;
import com.cgi.grocery.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/grocery")
public class GroceryPriceController {

    @Autowired
    PriceService priceService;

    @Autowired
    GroceryService groceryService;

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

    @GetMapping(value = "/sale-list/{itemName}")
    public ResponseEntity getGrocerySaleDataByItem(@PathVariable String itemName){
        List<PriceData> result = priceService.getGrocerySaleDataByItem(itemName);
        return result == null || result.isEmpty()
                ? ResponseEntity.unprocessableEntity().build()
                : ResponseEntity.ok(result);
    }

    @GetMapping(value = "/list")
    public ResponseEntity getGrocerySaleDataByItem(){
        Set<String> result = groceryService.getAllGroceryItems();
        return result == null || result.isEmpty()
                ? ResponseEntity.unprocessableEntity().build()
                : ResponseEntity.ok(result);
    }
}
