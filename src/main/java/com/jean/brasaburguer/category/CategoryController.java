package com.jean.brasaburguer.category;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponse> findAll(){
        return service.findAllActive();
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse findById(@PathVariable Long categoryId){
        return service.findById(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody @Valid CategoryRequest request){
        return service.create(request);
    }

    @PutMapping("/{categoryId}")
    public CategoryResponse update(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest request){
        return service.update(categoryId, request);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long categoryId){
        service.deactivate(categoryId);
    }
}
