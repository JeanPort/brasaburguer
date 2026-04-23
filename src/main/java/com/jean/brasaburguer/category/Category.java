package com.jean.brasaburguer.category;

import jakarta.persistence.*;

import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active;

    protected Category(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Category create(String name){
        validateName(name);
        var category = new Category();
        category.name = normalizeName(name);
        category.active = Boolean.TRUE;
        return category;
    }

    public void update(String name){
        validateName(name);
        this.name = normalizeName(name);
    }

    public boolean isActive(){
        return active;
    }

    public void deactivate(){
        this.active = Boolean.FALSE;
    }

    public static String normalizeName(String name) {
        return name.trim().toUpperCase(Locale.ROOT);
    }


    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name obrigatorio");
        }
    }
}
