package com.shopqa.model;

        import java.sql.Timestamp;

        public class Category {
            private int categoryId;
private String name;
private String description;
private Timestamp createdAt;

            public Category() {
            }

            public Category(int categoryId, String name, String description, Timestamp createdAt) {
                this.categoryId = categoryId;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
            }

public int getCategoryId() {
    return categoryId;
}

public void setCategoryId(int categoryId) {
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

public Timestamp getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
}

            @Override
            public String toString() {
                return "Category{" + "categoryId=" + categoryId + ", " + "name=" + name + ", " + "description=" + description + ", " + "createdAt=" + createdAt + "}";
            }
        }

