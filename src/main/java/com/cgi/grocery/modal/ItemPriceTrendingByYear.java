package com.cgi.grocery.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemPriceTrendingByYear {
    String itemName;
    List<Float> prices;
    List<String> years;
}
