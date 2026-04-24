package com.jean.brasaburguer.product;

import com.jean.brasaburguer.category.CategoryResponse;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailsResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        CategoryResponse category,
        List<ProductsAddonResponse> addons) {

    public static ProductDetailsResponse toProductDetailsResponse(Product product){

        var category = new CategoryResponse(
                product.getCategory().getId(),
                product.getCategory().getName()
        );

        return new ProductDetailsResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                category,
                product.getAddons()
                        .stream()
                        .map(ProductsAddonResponse::toProductAddonResponse)
                        .toList()
        );
    }
}
