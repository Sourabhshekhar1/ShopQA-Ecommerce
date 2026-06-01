<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Product Detail | ShopQA</title>
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
<c:if test="${empty product}">
  <h1>Product not found</h1>
  <a class="btn btn-primary" href="<c:url value='/products'/>">Back to Products</a>
</c:if>
<c:if test="${not empty product}">
  <section class="product-detail">
    <img src="${product.imageUrl}" alt="${product.name}" class="product-detail-img">
    <div>
      <h1>${product.name}</h1>
      <p class="price">$${product.price}</p>
      <p>${product.description}</p>
      <form method="post" action="<c:url value='/cart'/>" class="inline-form">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="productId" value="${product.productId}">
        <label for="qty">Qty</label>
        <input id="qty" type="number" name="qty" min="1" value="1">
        <button class="btn btn-primary" type="submit">Add to Cart</button>
      </form>
    </div>
  </section>
</c:if>
</main>
<footer class="site-footer">
  <div class="container">&copy; 2026 ShopQA. All rights reserved.</div>
</footer>
</body>
</html>
