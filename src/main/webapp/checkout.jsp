<%@ page import="dao.DAO" %>
<%@ page import="entity.Product" %>
<%@ page import="dao.CartDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.BillDAO" %>
<%@ page import="entity.Bill" %>
<%@ page import="entity.User" %>
<%@ page import="dao.CartDB_DAO" %>
<%@ page import="entity.Cart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/plugins.css" />
    <link rel="stylesheet" href="css/main.css" />
    <link rel="shortcut icon" type="image/x-icon" href="image/favicon.ico">
    <title>QCN Money</title>
</head>
<body class="">
<div class="site-wrapper">
    <%!

    %>
    <jsp:include page="header/header.jsp"></jsp:include>
    <nav aria-label="breadcrumb" class="breadcrumb-wrapper">
        <div class="container">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="index.html">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Thanh toán</li>
            </ol>
        </div>
    </nav>
    <main id="content" class="page-section sp-inner-page checkout-area-padding space-db--20">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <!-- Checkout Form s-->
                    <div class="checkout-form">
                        <div class="row row-40">
                            <form action="CheckoutControl" method="post">
                                <div class="col-12">
                                    <h3>${message}</h3>
                                    <h1 class="quick-title">Thanh toán</h1>


                                </div>
                                <div class="col-lg-7 mb--20">
                                    <!-- Billing Address -->
                                    <div id="billing-form" class="mb-40">
                                        <h4 class="checkout-title">Thông tin hóa đơn</h4>
                                        <div class="row">

                                            <div class="col-md-6 col-12 mb--20">
                                                <label>Tên*</label>
                                                <input type="text" placeholder="Tên"name="ten"required>
                                            </div>

                                            <div class="col-12 col-12 mb--20">
                                                <label>Phương thức thanh toán</label>
                                                <select class="nice-select"name="pt_thanhtoan">
                                                    <option>Thanh toán khi nhận hàng</option>

                                                </select>
                                            </div>
                                            <div class="col-12 mb--20">
                                                <label>Địa chỉ*</label>
                                                <input type="text" placeholder="Địa chỉ" name="dia_chi_giao_hang" required>
                                            </div>


                                        </div>
                                    </div>


                                    <div class="order-note-block mt--30">
                                        <label for="order-note">Ghi chú</label>
                                        <textarea id="order-note" cols="30" rows="10" class="order-note" placeholder="Ghi chú" name="ghichu" required></textarea>
                                    </div>

                                </div>
                                <div class="col-lg-5">
                                    <div class="row">
                                        <!-- Cart Total -->
                                        <div class="col-12">
                                            <div class="checkout-cart-total" style="position: absolute;margin-top: -503px;margin-left: 750px;">
                                                <h2 class="checkout-title">Đơn hàng của bạn</h2>


                                                <h4>Sản Phẩm <span>Tổng giá</span></h4>
                                                <ul>
                                                        <%
                                                    User currentUser = (User) session.getAttribute("user");
                                                    if (currentUser != null) {
                                                        CartDB_DAO cartDAO = new CartDB_DAO();
                                                        List<Cart> cartItems = cartDAO.getCartByUserId(Integer.parseInt(currentUser.getId()));
                                                        double cartTotal = cartDAO.getCartTotal(Integer.parseInt(currentUser.getId()));
                                                        request.setAttribute("cartItems", cartItems);
                                                        request.setAttribute("cartTotal", cartTotal);
                                                %>

                                                    <ul>
                                                        <c:forEach items="${cartItems}" var="item">
                                                            <li>
                                                            <span class="left">
                                                                ${item.product.name} X ${item.quantity}
                                                            </span>
                                                                <span class="right">
                                                                <fmt:formatNumber value="${item.totalPrice}"
                                                                                  type="currency"
                                                                                  currencySymbol="₫"/>
                                                            </span>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>

                                                    <p>Tổng tiền
                                                        <span>
                                                        <fmt:formatNumber value="${cartTotal}"
                                                                          type="currency"
                                                                          currencySymbol="₫"/>
                                                    </span>
                                                    </p>
                                                    <p>Phí vận chuyển
                                                        <span>
                                                        <fmt:formatNumber value="${cartTotal > 0 ? 35000 : 0}"
                                                                          type="currency"
                                                                          currencySymbol="₫"/>
                                                    </span>
                                                    </p>
                                                    <h4>Tổng cộng
                                                        <span>
                                                        <fmt:formatNumber value="${cartTotal + (cartTotal > 0 ? 35000 : 0)}"
                                                                          type="currency"
                                                                          currencySymbol="₫"/>
                                                    </span>
                                                    </h4>

                                                        <% } %>

                                                    <div class="term-block">
                                                        <input type="checkbox" id="accept_terms2" required>
                                                        <label for="accept_terms2">
                                                            Tôi đã đọc và chấp nhận các điều khoản & điều kiện
                                                        </label>
                                                    </div>
                                                    <button type="submit" class="place-order w-100">Đặt hàng</button>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>


    <jsp:include page="footer/footer.jsp"></jsp:include>
</div>


<script>
    function readFileContent() {
        var input = document.getElementById('verificationCode');
        var fileContentElement = document.getElementById('fileContent');

        if (input.files.length > 0) {
            var file = input.files[0];
            var reader = new FileReader();

            reader.onload = function (e) {
                var fileContent = e.target.result;
                fileContentElement.value = fileContent;
            };

            // Đọc nội dung của tệp tin
            reader.readAsText(file);
        }
    }
</script>


<script src="js/plugins.js"></script>
<script src="js/ajax-mail.js"></script>
<script src="js/custom.js"></script>
</body>
</html>
