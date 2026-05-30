<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Checkout | ShopQA</title>
  <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/navbar.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/forms.css'/>">
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
<h1>Checkout</h1>
<section class="checkout-layout">
  <div class="card">
    <h2>Order Summary</h2>
    <c:forEach var="item" items="${cartItems}">
      <p>${cartProducts[item.productId].name} x ${item.quantity}</p>
    </c:forEach>
    <p><strong>Total: $${cartTotal}</strong></p>
  </div>
  <form method="post" action="<c:url value='/order'/>" class="card form-card">
    <div class="form-group"><label for="shippingName">Name</label><input id="shippingName" name="shippingName" required></div>
    <div class="form-group"><label for="shippingAddress">Address</label><textarea id="shippingAddress" name="shippingAddress" required></textarea></div>
    <div class="form-group"><label for="shippingCity">City</label><input id="shippingCity" name="shippingCity" required></div>
    <div class="form-group"><label for="shippingZip">ZIP</label><input id="shippingZip" name="shippingZip" required></div>
    <button class="btn btn-primary" type="submit">Place Order</button>
  </form>
</section>
</main>
<footer class="site-footer">
  <div class="container">&copy; 2026 ShopQA. All rights reserved.</div>
</footer>
</body>
</html>
