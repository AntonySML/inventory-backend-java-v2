package com.inventory.inventory.Products.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inventory.inventory.Products.dto.CreateProductDTO;
import com.inventory.inventory.Products.dto.ProductDTO;
import com.inventory.inventory.Products.dto.UpdateProductDTO;
import com.inventory.inventory.Products.entity.Product;
import com.inventory.inventory.Products.repositories.ProductRepository;
import com.inventory.inventory.Users.entity.User;
import com.inventory.inventory.Users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final ProductRepository productRepository;
        private final UserRepository userRepository;

        public ProductDTO createProduct(CreateProductDTO dto, String userEmail) {

                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Product newProduct = Product.builder()
                                .name(dto.getName())
                                .description(dto.getDescription())
                                .price(dto.getPrice())
                                .quantity(dto.getQuantity())
                                .user(user)
                                .build();

                Product savedProduct = productRepository.save(newProduct);

                return convertToDTO(savedProduct);
        }

        public List<ProductDTO> getProductsByUser(String userEmail) {

                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Product> products = productRepository.findAllByUser(user);

                return products.stream()
                                .map(this::convertToDTO)
                                .collect(Collectors.toList());
        }

        private ProductDTO convertToDTO(Product product) {
                return ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .quantity(product.getQuantity())
                                .build();
        }

        public ProductDTO updateProduct(Long productId, UpdateProductDTO dto, String userEmail){

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if(!product.getUser().getEmail().equals(userEmail)){
            throw new RuntimeException("No tienes permiso para editar este producto");
        }

        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        
        if (dto.getPrice() != null) {
             product.setPrice(dto.getPrice());
        }
        
        if (dto.getQuantity() != null) {
             product.setQuantity(dto.getQuantity());
        }

        Product savedProduct = productRepository.save(product);

        return convertToDTO(savedProduct);
    }

        public ProductDTO deleteProduct(Long productId, String userEmail) {

                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                if (!product.getUser().getEmail().equals(userEmail)) {
                        throw new RuntimeException("No tienes permiso para eliminar este producto");
                }

                productRepository.deleteById(productId);

                return convertToDTO(product);
        }
}