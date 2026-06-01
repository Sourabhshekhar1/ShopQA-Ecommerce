# 🛒 ShopQA — E-Commerce Platform with Selenium QA

A fully functional Java-based e-commerce web application with an integrated Selenium WebDriver test suite, built as a complete learning project.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?style=for-the-badge&logo=mysql)
![Selenium](https://img.shields.io/badge/Selenium-4.x-green?style=for-the-badge&logo=selenium)
![Maven](https://img.shields.io/badge/Maven-3.9-red?style=for-the-badge&logo=apache-maven)

---

## 📌 Features

- 🔐 User Registration & Login with session management
- 🛍️ Product listing, search, and category filtering
- 🛒 Shopping cart (add, update, remove items)
- 💳 Checkout with shipping details
- 📦 Order placement and order history
- 🔧 Admin dashboard (manage products, orders, users)
- 🧪 Selenium WebDriver test suite using Page Object Model

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Jakarta Servlets, JSP (JSTL) |
| Frontend | HTML5, CSS3 (plain, no frameworks) |
| Database | MySQL 8.x via plain JDBC |
| Testing | Selenium WebDriver 4.x, JUnit 5 |
| Build | Apache Maven 3.9 |
| Server | Apache Tomcat 10.1 |

---

## ⚙️ Setup Instructions

### Prerequisites
- Java 17+
- Apache Maven 3.9+
- MySQL 8.x
- Apache Tomcat 10.1
- Google Chrome (for Selenium tests)

### 1. Clone the repository
```bash
git clone https://github.com/Sourabhshekhar1/ShopQA-Ecommerce.git
cd ShopQA-Ecommerce
```

### 2. Set up the database
Open MySQL Workbench and run `schema.sql`

### 3. Configure database connection
Edit `src/main/resources/db.properties`:
```
db.url=jdbc:mysql://localhost:3306/shopqa_db
db.username=root
db.password=YOUR_PASSWORD
```

### 4. Build and deploy
```bash
mvn clean package -DskipTests
```
Copy `target/shopqa.war` to your Tomcat `webapps/` folder.

### 5. Access the app
Open browser and go to: `http://localhost:8080/shopqa`

**Admin login:** `admin@shopqa.com` / `Admin@123`

---

## 🗂️ Project Structure

```
shopqa/
├── src/main/java/com/shopqa/
│   ├── model/        # User, Product, Order, Cart models
│   ├── dao/          # Database access layer (JDBC)
│   ├── servlet/      # HTTP request handlers
│   └── util/         # DBUtil, PasswordUtil, SessionUtil
├── src/main/webapp/
│   ├── WEB-INF/views/  # JSP pages
│   └── css/            # Stylesheets
├── src/test/java/com/shopqa/
│   ├── pages/        # Selenium Page Object classes
│   └── tests/        # JUnit 5 test classes
└── schema.sql        # Database schema + seed data
```

---

## 🧪 Running Tests

```bash
mvn test
```
Tests use WebDriverManager — ChromeDriver is managed automatically.

---

## 👤 Author

**Sourabh Shekhar**  
B.Tech Computer Science, IPU  
📧 sourabhshekhar2005@gmail.com  
🔗 [GitHub](https://github.com/Sourabhshekhar1) · [LeetCode](https://leetcode.com/u/shekharsourabh/)
