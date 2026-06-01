package com.shopqa.model;

            import java.math.BigDecimal;
import java.sql.Timestamp;

            public class Product {
                private int productId;
    private Integer categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQty;
    private String imageUrl;
    private boolean isActive;
    private Timestamp createdAt;

                public Product() {
                }

                public Product(int productId, Integer categoryId, String name, String description, BigDecimal price, int stockQty, String imageUrl, boolean isActive, Timestamp createdAt) {
                    this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQty = stockQty;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
                }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

                @Override
                public String toString() {
                    return "Product{" + "productId=" + productId + ", " + "categoryId=" + categoryId + ", " + "name=" + name + ", " + "description=" + description + ", " + "price=" + price + ", " + "stockQty=" + stockQty + ", " + "imageUrl=" + imageUrl + ", " + "isActive=" + isActive + ", " + "createdAt=" + createdAt + "}";
                }
            }

