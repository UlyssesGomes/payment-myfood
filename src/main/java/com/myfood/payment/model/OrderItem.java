package com.myfood.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private int quantity;
    private String description;
}
