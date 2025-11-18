package com.inventory.inventory.Products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;

}
