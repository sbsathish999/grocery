package com.cgi.grocery.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GroceryItem implements Serializable {
    String itemName;
}
