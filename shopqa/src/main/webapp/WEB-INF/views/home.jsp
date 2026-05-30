<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Home | ShopQA</title>
  <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/navbar.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/product.css'/>">
</head>
<body>
<header class="site-header">
  <nav class="navbar container">
    <a class="logo" href="<c:url value='/'/>">ShopQA</a>
    <div class="nav-links">
      <a href="<c:url value='/'/>">Home</a>
      <a href="<c:url value='/products'/>">Products</a>
      <a class="cart-link" href="<c:url value='/cart'/>">Cart <span class="cart-badge">${sessionScope.cartId != null ? '1' : '0'}</span></a>
      <c:if test="${sessionScope.userRole == 'admin'}"><a href="<c:url value='/admin?action=dashboard'/>">Admin</a></c:if>
      <c:if test="${empty sessionScope.userId}"><a href="<c:url value='/user?action=login'/>">Login</a></c:if>
      <c:if test="${not empty sessionScope.userId}"><a href="<c:url value='/user?action=logout'/>">Logout</a></c:if>
    </div>
  </nav>
</header>
<main class="container page">
<section class="page-title">
  <h1>Featured Products</h1>
  <p>Browse a small catalog wired for development and QA practice.</p>
</section>
<section class="category-strip">
  <c:forEach var="category" items="${categories}">
    <c:url var="categoryUrl" value="/products">
      <c:param name="categoryId" value="${category.categoryId}"/>
    </c:url>
    <a class="category-pill" href="${categoryUrl}">${category.name}</a>
  </c:forEach>
</section>
<section class="grid-4 product-grid">
  <c:forEach var="product" items="${products}">
    <article class="card product-card">
      <c:url var="productUrl" value="/products">
        <c:param name="id" value="${product.productId}"/>
      </c:url>
      <a href="${productUrl}">
        <img src="<c:url value='/${product.imageUrl}'/>" alt="${product.name}">
      </a>
      <h2>${product.name}</h2>
      <p class="price">$${product.price}</p>
      <form method="post" action="<c:url value='/cart'/>">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="productId" value="${product.productId}">
        <input type="hidden" name="qty" value="1">
        <button class="btn btn-primary" type="submit">Add to Cart</button>
      </form>
    </article>
  </c:forEach>
</section>
</main>
<footer class="site-footer">
  <div class="container">&copy; 2026 ShopQA. All rights reserved.</div>
</footer>
</body>
</html>
