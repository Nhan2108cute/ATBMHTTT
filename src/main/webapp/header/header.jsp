<%@ page import="dao.CartDB_DAO" %>
<%@ page import="entity.Cart" %>
<%@ page import="entity.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int cartCount = 0;
    double totalAmount = 0;
    List<Cart> cartItems = null;

    User currentUser = (User) session.getAttribute("user");
    if (currentUser != null) {
        CartDB_DAO cartDAO = new CartDB_DAO();
        cartItems = cartDAO.getCartByUserId(Integer.parseInt(currentUser.getId()));

        if (cartItems != null) {
            cartCount = cartItems.size();
            for (Cart cart : cartItems) {
                totalAmount += cart.getTotalPrice();
            }
        }
    }
%>

<header class="header petmark-header-1">
    <!-- Header top section -->
    <div class="header-top bg-ash">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-sm-6 text-center text-sm-start">
                    <h6 class="font-weight-300">Chào mừng đến Petmark</h6>
                </div>
            </div>
        </div>
    </div>

    <!-- Header middle section -->
    <div class="header-middle">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <!-- Logo -->
                <div class="col-lg-3 col-md-12 col-sm-4">
                    <div class="site-brand text-center text-lg-start">
                        <a href="home" class="brand-image">
                            <img src="image/main-logo.png" alt="Petmark Logo">
                        </a>
                    </div>
                </div>

                <!-- Search -->
                <div class="col-lg-5 col-md-7 order-3 order-md-2">
                    <form class="category-widget" action="search" method="get">
                        <input type="text" name="search" placeholder="Tìm kiếm sản phẩm" value="${txtS}">
                        <button class="search-submit" type="submit"><i class="fas fa-search"></i></button>
                    </form>
                </div>

                <!-- User and Contact Info -->
                <div class="col-lg-4 col-md-5 col-sm-8 order-2 order-md-3">
                    <div class="header-widget-2 text-center text-sm-end">
                        <div class="call-widget">
                            <p>Liên hệ: <i class="icon ion-ios-telephone"></i><span class="font-weight-mid">+84-012 345 678</span></p>
                        </div>
                        <ul class="header-links">
                            <c:choose>
                                <c:when test="${sessionScope.user == null}">
                                    <li>
                                        <a href="login.jsp"><i class="fas fa-user"></i> Đăng nhập hoặc đăng ký</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="profile"><i class="fas fa-user"></i> ${sessionScope.user.fullName}</a>
                                    </li>
                                    <li>
                                        <a href="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Navigation -->
    <div class="header-nav-wrapper">
        <div class="container">
            <div class="header-bottom-inner">
                <div class="row g-0">
                    <!-- Main Menu -->
                    <div class="col-lg-7 d-none d-lg-block">
                        <nav class="main-navigation l-widget">
                            <ul class="mainmenu">
                                <li class="mainmenu__item"><a href="home" class="mainmenu__link">Trang chủ</a></li>
                                <li class="mainmenu__item"><a href="list-product" class="mainmenu__link">Sản phẩm</a></li>
                                <li class="mainmenu__item"><a href="FAQ" class="mainmenu__link">Giải đáp</a></li>
                                <li class="mainmenu__item"><a href="contact" class="mainmenu__link">Liên hệ</a></li>
                                <li class="mainmenu__item"><a href="cart.jsp" class="mainmenu__link">Giỏ hàng</a></li>
                                <li class="mainmenu__item"><a href="blog" class="mainmenu__link">Tin tức</a></li>
                                <c:if test="${sessionScope.user.admin == 2}">
                                    <li class="mainmenu__item">
                                        <a href="admin-home" class="mainmenu__link" target="_blank">Trang Admin</a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>

                    <!-- Cart Widget -->
                    <div class="col-lg-2 col-6 offset-6 offset-md-0 col-md-3 order-3" style="margin-left: 25%">
                        <div class="cart-widget-wrapper slide-down-wrapper">
                            <div class="cart-widget slide-down--btn">
                                <div class="cart-icon">
                                    <i class="ion-bag"></i>
                                    <span class="cart-count-badge"><%=cartCount%></span>
                                </div>
                                <div class="cart-text">
                                    <strong>
                                        <span class="amount">
                                            <%=String.format("%.0f", totalAmount)%>₫
                                        </span>
                                    </strong>
                                </div>
                            </div>

                            <div class="slide-down--item">
                                <div class="cart-widget-box">
                                    <ul class="cart-items">
                                        <c:choose>
                                            <c:when test="${sessionScope.user != null && not empty cartItems}">
                                                <%
                                                    if (cartItems != null) {
                                                        for (Cart cart : cartItems) {
                                                %>
                                                <li class="single-cart">
                                                    <div class="cart-product">
                                                        <div class="cart-product-img">
                                                            <img src="<%=cart.getProduct().getImage()%>" alt="Sản phẩm">
                                                        </div>
                                                        <div class="product-details">
                                                            <h4 class="product-details--title"><%=cart.getProduct().getName()%></h4>
                                                            <span class="product-details--price">
                                                                <%=cart.getQuantity()%> x <%=String.format("%.0f", cart.getPrice())%>₫
                                                            </span>
                                                        </div>
                                                        <a href="removeFromCart?productId=<%= cart.getProductId()%>" class="remove-cart-item">
                                                            <i class="far fa-trash-alt"></i>
                                                        </a>
                                                    </div>
                                                </li>
                                                <%
                                                        }
                                                    }
                                                %>
                                                <li class="single-cart">
                                                    <div class="cart-product__subtotal">
                                                        <span class="subtotal--title">Tổng cộng</span>
                                                        <span class="subtotal--price"><%=String.format("%.0f", totalAmount)%>₫</span>
                                                    </div>
                                                </li>
                                                <li class="single-cart">
                                                    <div class="cart-buttons">
                                                        <a href="cart.jsp" class="btn btn-outlined">Xem giỏ hàng</a>
                                                        <a href="checkout.jsp" class="btn btn-outlined">Thanh toán</a>
                                                    </div>
                                                </li>
                                            </c:when>
                                        </c:choose>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Mobile Menu -->
                    <div class="col-12 d-flex d-lg-none order-2 mobile-absolute-menu">
                        <div class="mobile-menu"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>