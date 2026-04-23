package com.jean.brasaburguer.category;

public record CategoryResponse(Long id, String name) {

    public static CategoryResponse toCategoryResponse(Category category){
        return new CategoryResponse(category.getId(), category.getName());
    }
}
