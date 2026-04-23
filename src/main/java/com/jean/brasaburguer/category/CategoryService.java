package com.jean.brasaburguer.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllActive() {
        return repository.findByActiveTrue()
                .stream()
                .map(CategoryResponse::toCategoryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long categoryId) {
        var category = findByIdOrThrow(categoryId);
        return CategoryResponse.toCategoryResponse(category);
    }

    public CategoryResponse create(CategoryRequest request) {
        validateNameDuplicated(null, request.name());
        var category = Category.create(request.name());
        category = repository.save(category);
        return CategoryResponse.toCategoryResponse(category);
    }

    public CategoryResponse update(Long categoryId, CategoryRequest request) {
        var category = findByIdOrThrow(categoryId);
        validateNameDuplicated(categoryId, request.name());
        category.update(request.name());
        return CategoryResponse.toCategoryResponse(category);
    }

    public void deactivate(Long categoryId){
        var category = findByIdOrThrow(categoryId);
        category.deactivate();
    }

    private Category findByIdOrThrow(Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private void validateNameDuplicated(Long id, String name) {
        var normalizeName = Category.normalizeName(name);
        boolean nameExists = (id == null)
                ? repository.existsByName(normalizeName)
                : repository.existsByNameAndIdNot(normalizeName, id);
        if (nameExists) {
            throw new CategoryAlreadyExistsException(normalizeName);
        }
    }
}
