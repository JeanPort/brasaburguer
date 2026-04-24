package com.jean.brasaburguer.addon;

import java.math.BigDecimal;

public record AddonResponse(Long id, String name, BigDecimal price) {

    public static AddonResponse toAddonResponse(Addon addon){
        return new AddonResponse(addon.getId(), addon.getName(), addon.getPrice());
    }
}
