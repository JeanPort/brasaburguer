package com.jean.brasaburguer.product;

import com.jean.brasaburguer.addon.Addon;
import com.jean.brasaburguer.addon.AddonNotFoundException;
import com.jean.brasaburguer.addon.AddonRepository;
import com.jean.brasaburguer.category.CategoryNotFoundException;
import com.jean.brasaburguer.category.CategoryRepository;
import com.jean.brasaburguer.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    private final AddonRepository addonRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository, StorageService storageService, AddonRepository addonRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.storageService = storageService;
        this.addonRepository = addonRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(){
        return repository.findAll().stream().map(ProductResponse::toProductResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductDetailsResponse findById(Long productId){
        var product = findByIdOrThrow(productId);
        return ProductDetailsResponse.toProductDetailsResponse(product);
    }

    public ProductDetailsResponse create(ProductRequest request){
        var category = categoryRepository.findById(request.category().id())
                .orElseThrow(() -> new CategoryNotFoundException(request.category().id()));

        var product = Product.create(request.name(), request.description(), request.price(), category);
        repository.save(product);
        return ProductDetailsResponse.toProductDetailsResponse(product);
    }

    public ProductDetailsResponse updateInfo(Long productId, ProductRequest request){
        var product = findByIdOrThrow(productId);
        var category = categoryRepository.findById(request.category().id())
                .orElseThrow(() -> new CategoryNotFoundException(request.category().id()));
        product.update(request.name(), request.description(), request.price(), category);
        return ProductDetailsResponse.toProductDetailsResponse(product);
    }

    public void activate(Long productId){
        var product = findByIdOrThrow(productId);
        product.activate();
    }

    public void deactivate(Long productId){
        var product = findByIdOrThrow(productId);
        product.deactivate();
    }

    public void addAddon(Long productId, Long addonId){
        var product = findByIdOrThrow(productId);
        var addon = getAddonOrThrow(addonId);
        product.addAddon(addon);
    }

    public void removeAddon(Long productId, Long addonId){
        var product = findByIdOrThrow(productId);
        var addon = getAddonOrThrow(addonId);
        product.removeAddon(addon);
    }

    public ProductDetailsResponse changeImage(Long productId, MultipartFile file){
        var product = findByIdOrThrow(productId);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Arquivo deve ser uma imagem");
        }
        var imageUrl = storageService.upload(file);
        product.changeImage(imageUrl);
        return ProductDetailsResponse.toProductDetailsResponse(product);
    }

    private Product findByIdOrThrow(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
    private Addon getAddonOrThrow(Long addonId) {
        return addonRepository.findById(addonId)
                .orElseThrow(() -> new AddonNotFoundException(addonId));
    }
}
