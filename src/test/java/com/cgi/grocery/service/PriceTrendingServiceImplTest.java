package com.cgi.grocery.service;

import com.cgi.grocery.modal.ItemPriceTrendingByYear;
import com.cgi.grocery.modal.PriceData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceTrendingServiceImplTest {

    @InjectMocks
    PriceTrendingServiceImpl priceTrendingService;

    @Mock
    PriceService priceService;

    @Test
    public void testGetMaximumPriceDataByYear_WhenNoDataFoundAndReturnNull() {
        when(priceService.getGrocerySaleDataByItem("invalidItemName")).thenReturn(null);
        assertNull(priceTrendingService.getMaximumPriceDataByYear("invalidItemName"));
    }

    @Test
    public void testGetMaximumPriceDataByYear_WhenNoDataFoundAndReturnEmptyList() {
        when(priceService.getGrocerySaleDataByItem("unknonwnItem")).thenReturn(new ArrayList<>());
        assertNull(priceTrendingService.getMaximumPriceDataByYear("unknonwnItem"));
    }

    @Test
    public void testGetMaximumPriceDataByYear_WhenDataFound() throws ParseException {
        List<PriceData> priceDataList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormat.parse("01/02/2022");
        Date date2 = dateFormat.parse("01/04/2021");
        Date date3 = dateFormat.parse("01/05/2019");
        Date date4 = dateFormat.parse("01/11/2018");
        priceDataList.add(new PriceData("validItem", date1, 10.00f));
        priceDataList.add(new PriceData("validItem", date2, 20.00f));
        priceDataList.add(new PriceData("validItem", date3, 8.00f));
        priceDataList.add(new PriceData("validItem", date4, 9.00f));
        doReturn(priceDataList).when(priceService).getGrocerySaleDataByItem("validItem");
        ItemPriceTrendingByYear item = priceTrendingService.getMaximumPriceDataByYear("validItem");
        assertTrue(item != null);
        assertTrue(item.getItemName().equals("validItem"));
        assertTrue(item.getYears().get(0).equalsIgnoreCase("2018"));
        assertTrue(item.getYears().get(1).equalsIgnoreCase("2019"));
        assertTrue(item.getYears().get(2).equalsIgnoreCase("2021"));
        assertTrue(item.getYears().get(3).equalsIgnoreCase("2022"));
        assertTrue(item.getPrices().get(0).equals(9.00f));
        assertTrue(item.getPrices().get(1).equals(8.00f));
        assertTrue(item.getPrices().get(2).equals(20.00f));
        assertTrue(item.getPrices().get(3).equals(10.00f));
    }
}
