package com.inventory.inventory.Products.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory.Products.dto.CreateProductDTO;
import com.inventory.inventory.Products.dto.ProductDTO;
import com.inventory.inventory.Products.dto.UpdateProductDTO;
import com.inventory.inventory.Products.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody CreateProductDTO product, 
        @AuthenticationPrincipal UserDetails userDetails){
        
        String userEmail = userDetails.getUsername();

        return productService.createProduct(product, userEmail);
        
    }

    @GetMapping
    public List<ProductDTO> getProductsByUser(@AuthenticationPrincipal UserDetails userDetails){
        
        String userEmail = userDetails.getUsername();

        return productService.getProductsByUser(userEmail);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDTO product, 
        @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();
        
        return productService.updateProduct(id, product, userEmail);
    }

    @DeleteMapping("/{id}")
    public ProductDTO deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){

        String userEmail = userDetails.getUsername();

        return productService.deleteProduct(id, userEmail);
    }

}
