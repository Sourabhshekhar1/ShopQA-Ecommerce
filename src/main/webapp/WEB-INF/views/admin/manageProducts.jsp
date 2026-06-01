<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Products | ShopQA</title>
  <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/navbar.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/admin.css'/>">
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
<h1>Manage Products</h1>
<form method="post" action="<c:url value='/admin'/>" class="card admin-form">
  <input type="hidden" name="action" value="addProduct">
  <input name="name" placeholder="Product name" required>
  <select name="categoryId"><c:forEach var="category" items="${categories}"><option value="${category.categoryId}">${category.name}</option></c:forEach></select>
  <input name="price" type="number" step="0.01" min="0" placeholder="Price" required>
  <input name="stockQty" type="number" min="0" placeholder="Stock" required>
  <input name="imageUrl" placeholder="images/placeholder.png">
  <textarea name="description" placeholder="Description"></textarea>
  <button class="btn btn-primary" type="submit">Add Product</button>
</form>
<table class="data-table">
  <thead><tr><th>ID</th><th>Name</th><th>Price</th><th>Stock</th><th>Active</th><th>Actions</th></tr></thead>
  <tbody>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>${product.productId}</td>
        <td>${product.name}</td>
        <td>$${product.price}</td>
        <td>${product.stockQty}</td>
        <td>${product.active}</td>
        <td class="table-actions">
          <form method="post" action="<c:url value='/admin'/>">
            <input type="hidden" name="action" value="editProduct">
            <input type="hidden" name="productId" value="${product.productId}">
            <input type="hidden" name="categoryId" value="${product.categoryId}">
            <input type="hidden" name="name" value="${product.name}">
            <input type="hidden" name="description" value="${product.description}">
            <input type="hidden" name="price" value="${product.price}">
            <input type="hidden" name="stockQty" value="${product.stockQty}">
            <input type="hidden" name="imageUrl" value="${product.imageUrl}">
            <input type="hidden" name="isActive" value="1">
            <button class="btn btn-sm" type="submit">Edit</button>
          </form>
          <form method="post" action="<c:url value='/admin'/>">
            <input type="hidden" name="action" value="deleteProduct">
            <input type="hidden" name="productId" value="${product.productId}">
            <button class="btn btn-danger btn-sm" type="submit">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
</main>
<footer class="site-footer">
  <div class="container">&copy; 2026 ShopQA. All rights reserved.</div>
</footer>
</body>
</html>
