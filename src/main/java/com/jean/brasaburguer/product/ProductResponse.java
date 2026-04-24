package com.jean.brasaburguer.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(Long id, String name, String description, BigDecimal price, String imageUrl) {

    public static ProductResponse toProductResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}
