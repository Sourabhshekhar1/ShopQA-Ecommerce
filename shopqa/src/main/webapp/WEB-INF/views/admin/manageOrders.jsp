<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Orders | ShopQA</title>
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
<h1>Manage Orders</h1>
<table class="data-table">
  <thead><tr><th>Order</th><th>User</th><th>Total</th><th>Status</th><th>Created</th><th>Action</th></tr></thead>
  <tbody>
    <c:forEach var="order" items="${orders}">
      <tr>
        <td>#${order.orderId}</td>
        <td>${order.userId}</td>
        <td>$${order.totalAmount}</td>
        <td>${order.status}</td>
        <td>${order.createdAt}</td>
        <td>
          <form method="post" action="<c:url value='/admin'/>" class="status-form">
            <input type="hidden" name="action" value="updateStatus">
            <input type="hidden" name="orderId" value="${order.orderId}">
            <select name="status">
              <option value="pending">pending</option>
              <option value="processing">processing</option>
              <option value="shipped">shipped</option>
              <option value="delivered">delivered</option>
              <option value="cancelled">cancelled</option>
            </select>
            <button class="btn btn-sm" type="submit">Update</button>
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
