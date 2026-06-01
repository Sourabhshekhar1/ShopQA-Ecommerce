<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Confirmation | ShopQA</title>
  <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/navbar.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
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
<h1>Order #${order.orderId} placed!</h1>
<table class="data-table">
  <thead><tr><th>Product</th><th>Qty</th><th>Unit Price</th></tr></thead>
  <tbody>
    <c:forEach var="item" items="${orderItems}">
      <tr><td>${orderProducts[item.productId].name}</td><td>${item.quantity}</td><td>$${item.unitPrice}</td></tr>
    </c:forEach>
  </tbody>
</table>
<p class="cart-total"><strong>Total: $${order.totalAmount}</strong></p>
<a class="btn btn-primary" href="<c:url value='/'/>">Continue Shopping</a>
</main>
<footer class="site-footer">
  <div class="container">&copy; 2026 ShopQA. All rights reserved.</div>
</footer>
</body>
</html>
