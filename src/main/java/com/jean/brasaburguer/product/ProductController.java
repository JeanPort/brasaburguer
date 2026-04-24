package com.jean.brasaburguer.product;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/{productId}")
    public ProductDetailsResponse findById(@PathVariable Long productId){
        return service.findById(productId);
    }

    @PutMapping("/{productId}")
    public ProductDetailsResponse update(@PathVariable Long productId, @RequestBody @Valid ProductRequest request){
        return service.updateInfo(productId, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDetailsResponse create(@RequestBody @Valid ProductRequest request){
        return service.create(request);
    }

    @PostMapping(value = "/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductDetailsResponse changeImage(@PathVariable Long productId,@RequestParam(name = "file") MultipartFile file){
        return service.changeImage(productId, file);
    }

    @PatchMapping("/{productId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long productId){
        service.activate(productId);
    }

    @PatchMapping("/{productId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long productId){
        service.deactivate(productId);
    }

    @PostMapping("/{productId}/addons/{addonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addAddon(@PathVariable Long productId, @PathVariable Long addonId){
        service.addAddon(productId, addonId);
    }

    @DeleteMapping("/{productId}/addons/{addonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddon(@PathVariable Long productId, @PathVariable Long addonId){
        service.removeAddon(productId, addonId);
    }
}
