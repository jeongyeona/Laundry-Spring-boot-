package com.example.Laundry.dto;

import java.math.BigDecimal;

public class OrderItemResponseDto {
    private Integer num;
    private Integer code;
    private Integer inum;
    private Integer count;
    private String itemName;
    private BigDecimal price;

    public OrderItemResponseDto(Integer num, Integer code, Integer inum, Integer count, String itemName, BigDecimal price) {
        this.num = num;
        this.code = code;
        this.inum = inum;
        this.count = count;
        this.itemName = itemName;
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getInum() {
        return inum;
    }

    public Integer getCount() {
        return count;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
