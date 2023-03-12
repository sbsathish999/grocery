package com.cgi.grocery.service;

import com.cgi.grocery.config.GrocerySaleDataConfiguration;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {

    @InjectMocks
    PriceServiceImpl priceService;

    @Mock
    GrocerySaleDataConfiguration grocerySaleDataConfiguration;

    @Test
    public void testGetGroceryMaxSaleData_WhenNoDataFoundAndReturnNull() {
        when(grocerySaleDataConfiguration.read()).thenReturn(null);
        assertTrue(priceService.getGroceryMaxSaleData("invalidfilename", "invalidpath").isEmpty());
    }

    @Test
    public void testGetGroceryMaxSaleData_WhenNoDataFoundAndReturnEmptyList() {
        when(grocerySaleDataConfiguration.read()).thenReturn(new ArrayList<>());
        assertTrue(priceService.getGroceryMaxSaleData("unknowfilename", "invalidpath").isEmpty());
    }

    @Test
    public void testGetGroceryMaxSaleData_WhenDataFound() {
        List<PriceData> priceDataList = new ArrayList<>();
        Date date = new Date();
        priceDataList.add(new PriceData("testItem1", date, 10.00f));
        priceDataList.add(new PriceData("testItem2", date, 20.00f));
        priceDataList.add(new PriceData("testItem1", date, 8.00f));
        priceDataList.add(new PriceData("testItem2", date, 9.00f));
        doReturn(priceDataList).when(grocerySaleDataConfiguration).read();
        List<PriceData> result = priceService.getGroceryMaxSaleData("validfile.xlsx", "validpath");
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getItemName().equals("testItem1"));
        assertTrue(result.get(0).getPrice().equals(10.00f));
        assertTrue(result.get(1).getItemName().equals("testItem2"));
        assertTrue(result.get(1).getPrice().equals(20.00f));
    }

}
