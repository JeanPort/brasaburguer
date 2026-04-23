package com.jean.brasaburguer.category;

import com.jean.brasaburguer.common.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    private static final String MSG_CATEGORY_NOT_FOUND = "category with ID: %d not found";

    public CategoryNotFoundException(Long categoryId) {
        super(String.format(MSG_CATEGORY_NOT_FOUND, categoryId));
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
