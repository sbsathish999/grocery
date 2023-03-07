package com.cgi.grocery.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@Data
public class PriceData implements Serializable {
    //String datesk;
    String itemName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date date;
    Float price;
}
