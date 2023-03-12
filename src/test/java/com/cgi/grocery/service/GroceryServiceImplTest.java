package com.cgi.grocery.service;

import com.cgi.grocery.config.GrocerySaleDataConfiguration;
import com.cgi.grocery.modal.GroceryItem;
import com.cgi.grocery.modal.PriceData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroceryServiceImplTest {

    @InjectMocks
    GroceryServiceImpl groceryService;
    @Mock
    GrocerySaleDataConfiguration grocerySaleDataConfiguration;

    @Test
    public void testGetAllGroceryItems_WhenNoDataFoundAndReturnNull() {
        when(grocerySaleDataConfiguration.read()).thenReturn(null);
        assertTrue(groceryService.getAllGroceryItems().isEmpty());
    }

    @Test
    public void testGetAllGroceryItems_WhenNoDataFoundAndReturnEmptyList() {
        when(grocerySaleDataConfiguration.read()).thenReturn(new ArrayList<>());
        assertTrue(groceryService.getAllGroceryItems().isEmpty());
    }

    @Test
    public void testGetAllGroceryItems_WhenDataFound() {
        List<PriceData> priceDataList = new ArrayList<>();
        Date date = new Date();
        priceDataList.add(new PriceData("testItem1", date, 10.00f));
        priceDataList.add(new PriceData("testItem2", date, 20.00f));
        doReturn(priceDataList).when(grocerySaleDataConfiguration).read();
        List<GroceryItem> result = groceryService.getAllGroceryItems();
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getItemName().equals("testItem1"));
        assertTrue(result.get(1).getItemName().equals("testItem2"));
    }
}
