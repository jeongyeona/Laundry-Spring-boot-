package com.example.Laundry.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Items 테이블 매핑 엔티티
 */
@Entity
@Table(name = "items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inum", columnDefinition = "BIGINT AUTO_INCREMENT", updatable = false)
    private int inum;

    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Column(name = "item", length = 100, nullable = false)
    private String item;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    protected Items() {
        // JPA 기본 생성자
    }

    /**
     * 신규 Items 생성용 생성자 (inum은 DB에서 자동 생성)
     */
    public Items(String category, String item, BigDecimal price) {
        this.category = category;
        this.item = item;
        this.price = price;
    }

    // === getters & setters ===

    public int getInum() {
        return inum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // === equals, hashCode, toString ===

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Items)) return false;
        Items items = (Items) o;
        return Objects.equals(inum, items.inum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inum);
    }

    @Override
    public String toString() {
        return "Items{" +
                "inum=" + inum +
                ", category='" + category + '\'' +
                ", item='" + item + '\'' +
                ", price=" + price +
                '}';
    }
}
