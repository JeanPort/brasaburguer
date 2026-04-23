package com.jean.brasaburguer.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank String name
) {
}
