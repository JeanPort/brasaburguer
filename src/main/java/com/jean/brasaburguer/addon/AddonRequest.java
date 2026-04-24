package com.jean.brasaburguer.addon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AddonRequest(
        @NotBlank String name,
        @Positive BigDecimal price) {
}
