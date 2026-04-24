package com.jean.brasaburguer.product;

import com.jean.brasaburguer.addon.Addon;

import java.math.BigDecimal;

public record ProductsAddonResponse(Long id, String name, BigDecimal price) {

    public static ProductsAddonResponse toProductAddonResponse(Addon addon){
        return new ProductsAddonResponse(addon.getId(), addon.getName(), addon.getPrice());
    }
}
