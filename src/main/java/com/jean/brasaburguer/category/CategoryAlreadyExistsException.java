package com.jean.brasaburguer.category;

import com.jean.brasaburguer.common.AlreadyExistsException;

public class CategoryAlreadyExistsException extends AlreadyExistsException {
    private static final String MSG_CATEGORY_EXISTS = "category with NAME: %s exists";

    public CategoryAlreadyExistsException(String name) {
        super(String.format(MSG_CATEGORY_EXISTS, name));
    }

}
