package com.shopqa.model;

        import java.sql.Timestamp;

        public class Cart {
            private int cartId;
private Integer userId;
private String sessionId;
private Timestamp createdAt;

            public Cart() {
            }

            public Cart(int cartId, Integer userId, String sessionId, Timestamp createdAt) {
                this.cartId = cartId;
    this.userId = userId;
    this.sessionId = sessionId;
    this.createdAt = createdAt;
            }

public int getCartId() {
    return cartId;
}

public void setCartId(int cartId) {
    this.cartId = cartId;
}

public Integer getUserId() {
    return userId;
}

public void setUserId(Integer userId) {
    this.userId = userId;
}

public String getSessionId() {
    return sessionId;
}

public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
}

public Timestamp getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
}

            @Override
            public String toString() {
                return "Cart{" + "cartId=" + cartId + ", " + "userId=" + userId + ", " + "sessionId=" + sessionId + ", " + "createdAt=" + createdAt + "}";
            }
        }

