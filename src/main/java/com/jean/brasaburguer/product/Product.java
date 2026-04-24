package com.jean.brasaburguer.product;

import com.jean.brasaburguer.addon.Addon;
import com.jean.brasaburguer.category.Category;
import com.jean.brasaburguer.common.BusinessException;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "available", nullable = false)
    private boolean available;
    @Column(name = "image_url")
    private String imageUrl;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_product_addon",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "addon_id")
    )
    private Set<Addon> addons = new HashSet<>();

    protected Product() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public Set<Addon> getAddons() {
        return Collections.unmodifiableSet(addons);
    }

    public static Product create(String name, String description, BigDecimal price, Category category){
        validateName(name);
        validatePrice(price);
        validateCategory(category);

        var product = new Product();
        product.name = name.trim();
        product.description = description;
        product.price = price;
        product.available = Boolean.TRUE;
        product.category = category;
        return product;
    }

    public void update(String name, String description, BigDecimal price, Category category){
        validateName(name);
        validatePrice(price);
        validateCategory(category);

        this.name = name.trim();
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public void changeImage(String imageUrl){
        validateImage(imageUrl);
        this.imageUrl = imageUrl;
    }

    public void activate() {
        this.available = true;
    }

    public void deactivate() {
        this.available = false;
    }

    public void addAddon(Addon addon){
        if (!isAvailable()){
            throw new BusinessException("Produto inativo");
        }
        if (addon == null) {
            throw new IllegalArgumentException("Addon obrigatório");
        }
        if (!addon.isActive()) {
            throw new BusinessException("Addon deve estar ativo");
        }
        if (addons.contains(addon)){
            return;
        }
        addons.add(addon);
    }

    public void removeAddon(Addon addon){
        addons.remove(addon);
    }

    private void validateImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()){
            throw new IllegalArgumentException("Image obrigatioa");
        }
    }

    private static void validateCategory(Category category) {
        if (category == null){
            throw new IllegalArgumentException("Category obrigatoria");
        }
        if (!category.isActive()){
            throw new BusinessException("Categoria deve esta ativa");
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Preço invalido");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Name obrigatio");
        }
    }
}


