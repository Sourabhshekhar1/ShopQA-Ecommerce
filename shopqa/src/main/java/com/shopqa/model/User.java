package com.shopqa.model;

        import java.sql.Timestamp;

        public class User {
            private int userId;
private String fullName;
private String email;
private String passwordHash;
private String role;
private String profilePhoto;
private Timestamp createdAt;

            public User() {
            }

            public User(int userId, String fullName, String email, String passwordHash, String role, Timestamp createdAt) {
                this.userId = userId;
    this.fullName = fullName;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
    this.createdAt = createdAt;
            }

public int getUserId() {
    return userId;
}

public void setUserId(int userId) {
    this.userId = userId;
}

public String getFullName() {
    return fullName;
}

public void setFullName(String fullName) {
    this.fullName = fullName;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPasswordHash() {
    return passwordHash;
}

public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
}

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
}

public String getProfilePhoto() {
    return profilePhoto;
}

public void setProfilePhoto(String profilePhoto) {
    this.profilePhoto = profilePhoto;
}

public Timestamp getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
}

            @Override
            public String toString() {
                return "User{" + "userId=" + userId + ", " + "fullName=" + fullName + ", " + "email=" + email + ", " + "passwordHash=" + passwordHash + ", " + "role=" + role + ", " + "createdAt=" + createdAt + "}";
            }
        }

