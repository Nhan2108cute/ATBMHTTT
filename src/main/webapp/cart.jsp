<%@ page import="dao.CartDB_DAO" %>
<%@ page import="entity.Cart" %>
<%@ page import="entity.Product" %>
<%@ page import="entity.User" %>
<%@ page import="dao.DAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="css/plugins.css" />
	<link rel="stylesheet" href="css/main.css" />
	<link rel="shortcut icon" type="image/x-icon" href="image/favicon.ico">
	<title>Giỏ hàng - Petmark ❤️</title>

	<!-- Add AJAX functionality -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div class="site-wrapper">
	<jsp:include page="header/header.jsp"></jsp:include>

	<nav aria-label="breadcrumb" class="breadcrumb-wrapper">
		<div class="container">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="index.jsp">Trang chủ</a></li>
				<li class="breadcrumb-item active" aria-current="page">Giỏ hàng</li>
			</ol>
		</div>
	</nav>

	<!-- Cart Page Start -->
	<div class="cart_area cart-area-padding sp-inner-page--top">
		<div class="container">
			<div class="page-section-title">
				<h1>Giỏ Hàng</h1>
			</div>

			<%
				User currentUser = (User) session.getAttribute("user");
				if (currentUser == null) {
			%>
			<div class="alert alert-warning">
				Vui lòng <a href="login.jsp">đăng nhập</a> để xem giỏ hàng của bạn.
			</div>
			<%
			} else {
				CartDB_DAO cartDAO = new CartDB_DAO();
				List<Cart> cartItems = cartDAO.getCartByUserId(Integer.parseInt(currentUser.getId()));
				double cartTotal = cartDAO.getCartTotal(Integer.parseInt(currentUser.getId()));
				request.setAttribute("cartItems", cartItems);
				request.setAttribute("cartTotal", cartTotal);
			%>

			<div class="row">
				<div class="col-12">
					<div class="cart-table table-responsive mb--40">
						<table class="table">
							<thead>
							<tr>
								<th class="pro-remove"></th>
								<th class="pro-thumbnail">Hình Ảnh</th>
								<th class="pro-title">Tên Sản Phẩm</th>
								<th class="pro-price">Giá</th>
								<th class="pro-quantity">Số Lượng</th>
								<th class="pro-subtotal">Tổng Tiền</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${cartItems}" var="item">
								<tr>
									<td class="pro-remove">
										<a href="cart-remove?id=${item.product.id}"
										   class="remove-item">
											<i class="far fa-trash-alt"></i>
										</a>
									</td>
									<td class="pro-thumbnail">
										<img src="${item.product.image}"
											 alt="${item.product.name}" />
									</td>
									<td class="pro-title">
										<a href="product-details?id=${item.product.id}">
												${item.product.name}
										</a>
									</td>
									<td class="pro-price">
                                                <span>
                                                    <fmt:formatNumber value="${item.price}"
																	  type="currency"
																	  currencySymbol="₫"/>
                                                </span>
									</td>
									<td class="pro-quantity">
										<div class="pro-qty">
											<div class="count-input-block">
												<input type="number"
													   class="form-control text-center quantity-input"
													   value="${item.quantity}"
													   min="1"
													   data-product-id="${item.product.id}">
											</div>
										</div>
									</td>
									<td class="pro-subtotal">
                                                <span>
                                                    <fmt:formatNumber value="${item.totalPrice}"
																	  type="currency"
																	  currencySymbol="₫"/>
                                                </span>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<!-- Cart Summary -->
			<div class="row">
				<div class="col-lg-6">
					<div class="cart-summary">
						<div class="cart-summary-wrap">
							<h4><span>Tóm Tắt Đơn Hàng</span></h4>
							<p>Tổng tiền hàng
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
							<h2>Tổng thanh toán
								<span>
                                        <fmt:formatNumber value="${cartTotal + (cartTotal > 0 ? 35000 : 0)}"
														  type="currency"
														  currencySymbol="₫"/>
                                    </span>
							</h2>
						</div>
						<div class="cart-summary-button">
							<a href="checkout.jsp" class="checkout-btn c-btn btn--primary">
								Thanh toán
							</a>
							<button class="update-btn c-btn btn--secondary">
								Cập nhật giỏ hàng
							</button>
						</div>
					</div>
				</div>
			</div>
			<% } %>
		</div>
	</div>
	<!-- Cart Page End -->

	<jsp:include page="footer/footer.jsp"></jsp:include>
</div>

<!-- Add JavaScript for cart functionality -->
<script>
	$(document).ready(function() {
		// Handle quantity changes
		$('.quantity-input').change(function() {
			const productId = $(this).data('product-id');
			const quantity = $(this).val();

			$.ajax({
				url: 'cart-update',
				method: 'POST',
				data: {
					productId: productId,
					quantity: quantity
				},
				success: function(response) {
					if (response.success) {
						location.reload(); // Refresh to show updated totals
					} else {
						alert(response.message || 'Cập nhật thất bại');
					}
				},
				error: function() {
					alert('Đã xảy ra lỗi khi cập nhật giỏ hàng');
				}
			});
		});

		// Handle remove item
		$('.remove-item').click(function(e) {
			if (!confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?')) {
				e.preventDefault();
			}
		});

		// Update cart button
		$('.update-btn').click(function() {
			location.reload();
		});
	});
</script>

<!-- Include your existing scripts -->
<script src="js/plugins.js"></script>
<script src="js/custom.js"></script>
</body>
</html>