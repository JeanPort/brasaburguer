package com.jean.brasaburguer.product;

import com.jean.brasaburguer.common.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    private static final String MSG_PRODUCT_NOT_FOUND = "Product with ID: %d not found";

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super(String.format(MSG_PRODUCT_NOT_FOUND, id));
    }
}
