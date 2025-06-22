package com.example.Laundry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentCreateDto {

    @JsonProperty("impUid")  // JSON의 camelCase 키
    private String impUid;

    @JsonProperty("merchantUid")
    private String merchantUid;

    // 기본 생성자
    public PaymentCreateDto() {}

    // getter/setter
    public String getImpUid() {
        return impUid;
    }

    public void setImpUid(String impUid) {
        this.impUid = impUid;
    }

    public String getMerchantUid() {
        return merchantUid;
    }

    public void setMerchantUid(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
