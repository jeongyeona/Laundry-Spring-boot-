// Domain: com.example.Laundry.domain.ServiceOrder.java
package com.example.Laundry.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "service_order")
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @Column(length = 100, nullable = false)
    private String orderer;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(name = "order_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal orderPrice;

    @Column(name = "order_addr", length = 100, nullable = false)
    private String orderAddr;

    @Column(nullable = false)
    private LocalDate regdate;

    @Column(name = "reservation_date", length = 100, nullable = false)
    private String reservationDate;

    @Column(length = 500)
    private String request;

    @Column(length = 50)
    private String state;

    @Column(name = "get_invoice_num")
    private Integer getInvoiceNum;

    @Column(name = "send_invoice_num")
    private Integer sendInvoiceNum;

    // Getters and Setters
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getOrderer() { return orderer; }
    public void setOrderer(String orderer) { this.orderer = orderer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getOrderPrice() { return orderPrice; }
    public void setOrderPrice(BigDecimal orderPrice) { this.orderPrice = orderPrice; }

    public String getOrderAddr() { return orderAddr; }
    public void setOrderAddr(String orderAddr) { this.orderAddr = orderAddr; }

    public LocalDate getRegdate() { return regdate; }
    public void setRegdate(LocalDate regdate) { this.regdate = regdate; }

    public String getReservationDate() { return reservationDate; }
    public void setReservationDate(String reservationDate) { this.reservationDate = reservationDate; }

    public String getRequest() { return request; }
    public void setRequest(String request) { this.request = request; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Integer getGetInvoiceNum() { return getInvoiceNum; }
    public void setGetInvoiceNum(Integer getInvoiceNum) { this.getInvoiceNum = getInvoiceNum; }

    public Integer getSendInvoiceNum() { return sendInvoiceNum; }
    public void setSendInvoiceNum(Integer sendInvoiceNum) { this.sendInvoiceNum = sendInvoiceNum; }
}
