package com.inventory.inventory.Products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateProductDTO {

    @Size(min = 1, message = "El nombre no puede estar vacío")
    private String name;

    private String description;

    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double price; 

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer quantity;
}
