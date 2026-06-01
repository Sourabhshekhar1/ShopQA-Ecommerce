# ShopQA — Codex Agent Rules

## Stack (DO NOT deviate)
- Java 17
- Jakarta Servlet API 6.0 (use jakarta.* namespace, NOT javax.*)
- JSTL 3.x (use jakarta.servlet.jsp.jstl taglib URI)
- MySQL 8.x via plain JDBC — NO Hibernate, NO JPA, NO Spring
- Maven build (pom.xml already configured — do not alter dependency versions)
- Tomcat 10.x deployment target

## Package structure
- All source code under: com.shopqa.*
- Models: com.shopqa.model
- DAOs: com.shopqa.dao
- Servlets: com.shopqa.servlet
- Utilities: com.shopqa.util
- Test pages (POM): com.shopqa.pages
- Test classes: com.shopqa.tests

## Database
- DB name: shopqa_db
- Connection via DBUtil.getConnection() — always close in finally block
- Always use PreparedStatement — never concatenate SQL strings
- Passwords stored as SHA-256 hex via PasswordUtil

## Selenium tests
- All tests go in /src/test/java/com/shopqa/tests/
- All Page Object classes go in /src/test/java/com/shopqa/pages/
- Use WebDriverManager to manage ChromeDriver automatically
- Base URL loaded from test config: http://localhost:8080/shopqa
- Follow Page Object Model strictly — no raw driver calls inside test methods

## Code style
- No inline CSS anywhere — all styles in /src/main/webapp/css/
- No raw SQL string concatenation — PreparedStatement only
- Session data keys: "userId", "userRole", "fullName", "cartId"
- Servlet action parameter name: "action" (e.g. ?action=login)
- Forward to JSPs via RequestDispatcher — never print HTML from servlets

