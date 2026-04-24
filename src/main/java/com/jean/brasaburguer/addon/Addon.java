package com.jean.brasaburguer.addon;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_addon")
public class Addon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false, scale = 2, precision = 10)
    private BigDecimal price;
    @Column(name = "active", nullable = false)
    private boolean active;

    protected Addon(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    public static Addon create(String name, BigDecimal price) {
        validateName(name);
        validatePrice(price);
        var addon = new Addon();
        addon.name = name.trim();
        addon.price = price;
        addon.active = Boolean.TRUE;
        return addon;
    }

    public void update(String name, BigDecimal price){
        validateName(name);
        validatePrice(price);

        this.name = name.trim();
        this.price = price;
    }

    public void acivate(){
        this.active = Boolean.TRUE;
    }

    public void deactivate(){
        this.active = Boolean.FALSE;
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price invalid");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Name obrigatio");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addon addon = (Addon) o;
        return Objects.equals(id, addon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
