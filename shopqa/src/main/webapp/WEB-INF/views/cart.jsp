<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cart | ShopQA</title>
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
<h1>Your Cart</h1>
<table class="data-table cart-table">
  <thead><tr><th>Product</th><th>Price</th><th>Qty</th><th>Subtotal</th><th></th></tr></thead>
  <tbody>
    <c:forEach var="item" items="${cartItems}">
      <tr>
        <td>${cartProducts[item.productId].name}</td>
        <td>$${cartProducts[item.productId].price}</td>
        <td>
          <form method="post" action="<c:url value='/cart'/>" class="qty-form">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="cartItemId" value="${item.cartItemId}">
            <input type="number" name="qty" min="0" value="${item.quantity}">
            <button class="btn btn-sm" type="submit">Update</button>
          </form>
        </td>
        <td>$${cartProducts[item.productId].price * item.quantity}</td>
        <td>
          <form method="post" action="<c:url value='/cart'/>">
            <input type="hidden" name="action" value="remove">
            <input type="hidden" name="cartItemId" value="${item.cartItemId}">
            <button class="btn btn-danger btn-sm" type="submit">Remove</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
<c:if test="${empty cartItems}"><p class="empty-state">Your cart is empty.</p></c:if>
<section class="cart-total card">
  <strong>Total: $${cartTotal}</strong>
  <a class="btn btn-primary" href="<c:url value='/checkout'/>">Checkout</a>
</section>
</main>
<footer class="site-footer">
  <div class="container">&copy; 2026 ShopQA. All rights reserved.</div>
</footer>
</body>
</html>
