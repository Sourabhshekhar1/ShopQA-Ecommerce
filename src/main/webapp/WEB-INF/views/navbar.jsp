<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<nav class="navbar">
  <div class="navbar-inner">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand">ShopQA</a>
    <ul class="navbar-links">
      <li><a href="${pageContext.request.contextPath}/">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/products">Products</a></li>
      <li>
        <a href="${pageContext.request.contextPath}/cart" class="cart-link">
          🛒 Cart
        </a>
      </li>
      <c:choose>
        <c:when test="${not empty sessionScope.userId}">
          <li><span style="color:var(--text-light);font-size:0.9rem;">Hi, ${sessionScope.fullName}</span></li>
          <c:if test="${sessionScope.userRole == 'admin'}">
            <li><a href="${pageContext.request.contextPath}/admin?action=dashboard">Admin</a></li>
          </c:if>
          <li><a href="${pageContext.request.contextPath}/user?action=logout">Logout</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="${pageContext.request.contextPath}/user?action=login">Login</a></li>
          <li><a href="${pageContext.request.contextPath}/user?action=register">Register</a></li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
</nav>