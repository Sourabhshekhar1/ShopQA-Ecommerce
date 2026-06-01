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
  <!-- Welcome Popup -->
<div id="welcomePopup" style="
  display: flex;
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.5);
  z-index: 9999;
  align-items: center;
  justify-content: center;
">
  <div style="
    background: white;
    border-radius: 16px;
    padding: 2.5rem;
    max-width: 420px;
    width: 90%;
    text-align: center;
    box-shadow: 0 20px 60px rgba(0,0,0,0.3);
    animation: popIn 0.4s ease;
  ">
    <div style="font-size:3rem; margin-bottom:1rem;">🛍️</div>
    <h2 style="
      color: #2563EB;
      font-size: 1.6rem;
      font-weight: 700;
      margin-bottom: 0.5rem;
    ">Welcome to ShopQA!</h2>
    <p style="
      color: #6B7280;
      font-size: 0.95rem;
      line-height: 1.7;
      margin-bottom: 1.5rem;
    ">
      For the best shopping experience,<br>
      <strong>sign up today</strong> and enjoy exclusive deals,
      order tracking, and a seamless checkout!
    </p>
    <div style="display:flex; gap:1rem; justify-content:center;">
      <a href="${pageContext.request.contextPath}/user?action=register" style="
        background: #2563EB;
        color: white;
        padding: 0.6rem 1.5rem;
        border-radius: 8px;
        font-weight: 600;
        text-decoration: none;
        font-size: 0.95rem;
      ">Sign Up Free</a>
      <button onclick="closePopup()" style="
        background: #F3F4F6;
        color: #374151;
        padding: 0.6rem 1.5rem;
        border-radius: 8px;
        font-weight: 600;
        border: none;
        cursor: pointer;
        font-size: 0.95rem;
      ">Maybe Later</button>
    </div>
  </div>
</div>

<style>
@keyframes popIn {
  from { transform: scale(0.8); opacity: 0; }
  to   { transform: scale(1);   opacity: 1; }
}
</style>

<script>
  // Only show popup if user has not seen it before
  function closePopup() {
    document.getElementById('welcomePopup').style.display = 'none';
    localStorage.setItem('popupSeen', 'true');
  }

  window.onload = function() {
    var seen = localStorage.getItem('popupSeen');
    var loggedIn = '${not empty sessionScope.userId}' === 'true';
    if (seen || loggedIn) {
      document.getElementById('welcomePopup').style.display = 'none';
    }
  }
</script>
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
      <img src="${product.imageUrl}" alt="${product.name}" class="product-card-img">
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
<!-- About Section -->
<section class="about-section" style="
  margin-top: 3rem;
  background: #ffffff;
  border: 1px solid var(--border);
  border-radius: 12px;
  padding: 2.5rem;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0,0,0,0.07);
">
  <div>
    <h2 style="font-size:1.8rem; font-weight:700; color:var(--primary); margin-bottom:1rem;">
      About ShopQA
    </h2>
    <p style="color:var(--text-light); line-height:1.8; margin-bottom:1rem;">
      ShopQA is a modern e-commerce platform built for quality and reliability. 
      We offer a curated selection of Electronics, Clothing, and Books — 
      all tested and verified for the best shopping experience.
    </p>
    <p style="color:var(--text-light); line-height:1.8; margin-bottom:1.5rem;">
      Our platform is built with Java, Selenium QA testing, and a focus on 
      performance — ensuring every order is processed smoothly and securely.
    </p>
    <div style="display:flex; gap:2rem;">
      <div style="text-align:center;">
        <div style="font-size:2rem; font-weight:700; color:var(--primary);">6+</div>
        <div style="font-size:0.85rem; color:var(--text-light);">Products</div>
      </div>
      <div style="text-align:center;">
        <div style="font-size:2rem; font-weight:700; color:var(--primary);">3</div>
        <div style="font-size:0.85rem; color:var(--text-light);">Categories</div>
      </div>
      <div style="text-align:center;">
        <div style="font-size:2rem; font-weight:700; color:var(--primary);">100%</div>
        <div style="font-size:0.85rem; color:var(--text-light);">QA Tested</div>
      </div>
    </div>
  </div>
  <div style="text-align:center;">
    <div style="
      background: linear-gradient(135deg, #2563EB, #1E40AF);
      border-radius: 12px;
      padding: 2rem;
      color: white;
    ">
      <div style="font-size:3rem; margin-bottom:1rem;">🛒</div>
      <h3 style="font-size:1.3rem; font-weight:700; margin-bottom:0.5rem;">
        Why Choose ShopQA?
      </h3>
      <ul style="list-style:none; text-align:left; line-height:2;">
        <li>✅ Quality tested products</li>
        <li>✅ Secure checkout process</li>
        <li>✅ Fast order processing</li>
        <li>✅ Easy returns policy</li>
        <li>✅ 24/7 customer support</li>
       <li style="margin-top:1rem; border-top:1px solid rgba(255,255,255,0.3); padding-top:1rem;">
        👤 <strong>Managed by:</strong> Sourabh Shekhar
      </li>
      <li>📧 <a href="mailto:sourabhshekhar2005@gmail.com" style="color:white;">sourabhshekhar2005@gmail.com</a></li>
      <li>📞 9818698922</li>
      </ul>
    </div>
  </div>
</section>
</main>
<footer class="site-footer">
  <div class="container">&copy; 2026 ShopQA. All rights reserved.</div>
</footer>
</body>
</html>
