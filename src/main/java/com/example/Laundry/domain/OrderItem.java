// Domain: com.example.Laundry.domain.OrderItem.java
package com.example.Laundry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false)
    private Integer inum;

    @Column(name = "count", nullable = false)
    private Integer count;

    // Getters and Setters
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public Integer getInum() { return inum; }
    public void setInum(Integer inum) { this.inum = inum; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
}
