package com.shopqa.model;

            import java.math.BigDecimal;
import java.sql.Timestamp;

            public class Order {
                private int orderId;
    private Integer userId;
    private BigDecimal totalAmount;
    private String status;
    private String shippingName;
    private String shippingAddress;
    private String shippingCity;
    private String shippingZip;
    private Timestamp createdAt;

                public Order() {
                }

                public Order(int orderId, Integer userId, BigDecimal totalAmount, String status, String shippingName, String shippingAddress, String shippingCity, String shippingZip, Timestamp createdAt) {
                    this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingName = shippingName;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingZip = shippingZip;
        this.createdAt = createdAt;
                }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingZip() {
        return shippingZip;
    }

    public void setShippingZip(String shippingZip) {
        this.shippingZip = shippingZip;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

                @Override
                public String toString() {
                    return "Order{" + "orderId=" + orderId + ", " + "userId=" + userId + ", " + "totalAmount=" + totalAmount + ", " + "status=" + status + ", " + "shippingName=" + shippingName + ", " + "shippingAddress=" + shippingAddress + ", " + "shippingCity=" + shippingCity + ", " + "shippingZip=" + shippingZip + ", " + "createdAt=" + createdAt + "}";
                }
            }

