package com.jean.brasaburguer.product;

import jakarta.validation.constraints.NotNull;

public record CategoryIdRequest(@NotNull Long id) {
}
