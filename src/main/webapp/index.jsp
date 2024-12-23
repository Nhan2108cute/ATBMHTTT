<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 30/12/2022
  Time: 21:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/plugins.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="shortcut icon" type="image/x-icon" href="image/favicon.ico">
    <link href="https://fonts.googleapis.com/css2?family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">
    <title>QNC Money</title>
    <style>
        .popup-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.6); /* Hiệu ứng mờ */
            z-index: 999; /* Đặt overlay bên trên mọi nội dung khác */
            display: none; /* Ẩn overlay mặc định */
        }
        .popup {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: #fff;
            width: 40%;
            height: 15%;
            padding: 20px;
            border-radius: 10px;
            z-index: 1000; /* Đặt popup bên trên overlay */
            text-align: center;
        }
        input[type="file"]::-webkit-file-upload-button {
            visibility: hidden; /* Ẩn nút mặc định */
        }
        input[type="file"]::before {
            content: "Import";
            display: inline-block;
            background-color: #198754;
            color: white;
            padding: 0px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            text-align: center;
        }
        input[type="file"]:hover::before {
            background-color: #198754;
        }
        .btnToCreateKey{
            display: flex;
            justify-content: center;
        }
        .btn-success{
            width: 160px;
        }

    </style>
</head>
<body class="petmark-theme-2">
<c:if test="${sessionScope.user == null}">

</c:if>
<c:if test="${sessionScope.user != null && sessionScope.keyExists}">

</c:if>
<c:if test="${sessionScope.user != null && !sessionScope.keyExists}">
    <div class="popup-overlay"></div> <!-- Overlay nằm bên dưới popup -->
    <div class="popup">
        <span class="thong-tin-thanh-toan">
            <h5>Bạn chưa tạo khoá, hãy tạo khoá để tiếp tục mua hàng</h5>
        </span>
        <div class="btnToCreateKey">
            <input value="Đi đến trang tạo key" class="btn btn-success" style="float: right" onclick="navigateToKeyManagement()">
        </div>
    </div>
</c:if>
<div class="site-wrapper">
    <jsp:include page="header/header.jsp"></jsp:include>
    <section class="hero-area-two">
        <div class="container">
            <div class="row">
                <div class="col-xl-9 col-lg-8 col-md-7">
                    <div class=" petmark-slick-slider  home-slider" data-slick-setting='{
                                "autoplay": true,
                                "autoplaySpeed": 8000,
                                "slidesToShow": 1,
                                "dots": true
                                }'>
                        <div class="single-slider home-content bg-image"
                             data-bg="image/bg-images/home-2/slider-2.jpg">
                            <div class="container">
                                <div class="row">
                                    <div class="col-lg-10">
                                        <h2><span class="text-primary">QCN Money</span> cửa hàng <br> tiền sưu tầm</h2>
                                        <h4 class="mt--30">Lựa chọn tốt nhất dành cho người dùng thích sưu tầm tiền</h4>
                                        <div class="slider-btn mt--30">
                                            <a href="list-product" class="btn btn-outlined--white btn-rounded">
                                                Mua ngay</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <span class="herobanner-progress"></span>
                        </div>
                        <div class="single-slider home-content bg-image"
                             data-bg="image/bg-images/home-2/slider-1.jpg">
                            <div class="container">
                                <div class="row">
                                    <div class="col-lg-10">
                                        <h4>Cửa hàng tiền sưu tầm</h4>
                                        <h2 class="mt--20">Đến với <br> chúng tôi</h2>
                                        <div class="slider-btn mt--30">
                                            <a href="list-product" class="btn btn-outlined--white btn-rounded">Mua ngay
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <span class="herobanner-progress"></span>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-lg-4 col-md-5 pt--50 pt-md-0">
                    <a class="promo-image overflow-image mb-0">
                        <img src="image/product/hero-promo-product.jpg" alt="">
                    </a>
                </div>
            </div>
        </div>
    </section>
    <div class="container pt--50">
        <div class="policy-block border-four-column">
            <div class="row">
                <div class="col-lg-3 col-sm-6">
                    <div class="policy-block-single">
                        <div class="icon">
                            <span class="ti-truck"></span>
                        </div>
                        <div class="text">
                            <h3>Miễn Phí giao hàng</h3>
                            <p>Đơn từ 200k trở lên</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="policy-block-single">
                        <div class="icon">
                            <span class="ti-credit-card"></span>
                        </div>
                        <div class="text">
                            <h3>Thanh toán</h3>
                            <p>Thanh toán khi giao hàng
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="policy-block-single">
                        <div class="icon">
                            <span class="ti-gift"></span>
                        </div>
                        <div class="text">
                            <h3>Quà tặng miễn phí</h3>
                            <p>Khi mua 1 đơn hàng </p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="policy-block-single">
                        <div class="icon">
                            <span class="ti-headphone-alt"></span>
                        </div>
                        <div class="text">
                            <h3>Hỗ trợ khách hàng 24/7</h3>
                            <p>Trực tuyến 24h mỗi ngày</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Home -2 => Slider Block 1 -->
    <div class="pt--50">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-xl-9">
                    <div class="slider-header-block tab-header d-flex border-bottom mb--20">
                        <div class="block-title-2 mb-0 border-0">
                            <h2>Tiền Kỉ Niệm</h2>
                        </div>
                        <ul class="pm-tab-nav tab-nav-style-2 nav nav-tabs" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="home-tab" data-bs-toggle="tab" href="#"
                                   role="tab" aria-controls="home" aria-selected="true"> 1</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="profile-tab" data-bs-toggle="tab" href="#" role="tab"
                                   aria-controls="profile" aria-selected="false"> 2</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="contact-tab" data-bs-toggle="tab" href="#" role="tab"
                                   aria-controls="contact" aria-selected="false"> 3</a>
                            </li>
                        </ul>
                    </div>
                    <div class=" tab-content pm-slider-tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <div class="petmark-slick-slider petmark-slick-slider--wrapper-2 border grid-column-slider  arrow-type-two"
                                 data-slick-setting='{
                                        "autoplay": true,
                                        "autoplaySpeed": 3000,
                                        "slidesToShow": 4,
                                        "rows" :2,
                                        "arrows": true
                                        }' data-slick-responsive='[
                                        {"breakpoint":1200, "settings": {"slidesToShow": 3} },
                                        {"breakpoint":768, "settings": {"slidesToShow": 2} },
                                        {"breakpoint":480, "settings": {"slidesToShow": 1,"rows" :1} }
                                        ]'>
                                <c:forEach items="${listT}" var="t">
                                    <div class="single-slide">
                                        <div class="pm-product">
                                            <div class="image">
                                                <a href="detail?pID=${t.id}"><img
                                                        src="${t.image}" alt=""></a>
                                                <span class="onsale-badge">Sale!</span>
                                            </div>
                                            <div class="content">
                                                <h3><a href="detail?pID=${p.id}"> ${t.name} </a></h3>
                                                <div class="price text-orange">

                                                    <span>${t.price} VND</span>
                                                </div>
                                                <div class="btn-block">
                                                    <a href="cart-home?&id=${t.id}"
                                                       class="btn btn-outlined btn-rounded btn-mid">Thêm vào giỏ</a>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-xl-3 pt--50 pt-lg-0">
                    <div class="block-title-2">
                        <h2>Bán Chạy Nhất</h2>
                    </div>
                    <!--Three Row One Column Slider -->
                    <div class="petmark-slick-slider petmark-slick-slider--wrapper-2 border one-column-slider three-row"
                         data-slick-setting='{
                            "autoplaySpeed": 3000,
                            "slidesToShow": 1,
                            "rows" :3,
                            "arrows": true
                            }' data-slick-responsive='[
                            {"breakpoint":991, "settings": {"slidesToShow": 1} },
                            {"breakpoint":575, "settings": {"slidesToShow": 1} }
                            ]'>
                        <c:forEach items="${listB}" var="t">
                            <div class="single-slide">
                                <div class="pm-product product-type-list">
                                    <a href="detail?pID=${t.id}" class="image">
                                        <img src="${t.image}" alt="">
                                    </a>
                                    <div class="content">
                                        <h3><a href="detail?pID=${t.id}"> ${t.name} </a></h3>
                                        <div class="price text-orange">

                                            <span>${t.price}</span>
                                        </div>
                                        <div class="rating-widget mt--20">
                                            <a href="#" class="single-rating"><i class="fas fa-star"></i></a>
                                            <a href="#" class="single-rating"><i class="fas fa-star"></i></a>
                                            <a href="#" class="single-rating"><i class="fas fa-star"></i></a>
                                            <a href="#" class="single-rating"><i class="fas fa-star-half-alt"></i></a>
                                            <a href="#" class="single-rating"><i class="far fa-star"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Home-2 => Promotion Block 1 -->
<section class="pt--50 space-db--30">
    <h2 class="d-none">Promotion Block
    </h2>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <a class="promo-image overflow-image">
                    <img src="image/product/promo-product-image-big--home-2.jpg" alt="">
                </a>
            </div>
            <div class="col-md-6">
                <a class="promo-image overflow-image">
                    <img src="image/product/promo-product-image-big-2--home-2.jpg" alt="">
                </a>
            </div>
        </div>
    </div>
</section>
<!-- Home 2 => Slider bLock 2 -->
<div class="pt--50">
    <div class="container">
        <div class="block-title-2">
            <h2>SẢN PHẤM MỚI</h2>
        </div>
        <div class="petmark-slick-slider petmark-slick-slider--wrapper-2 border grid-column-slider "
             data-slick-setting='{
                "autoplay": true,
                "autoplaySpeed": 3000,
                "slidesToShow": 5,
                "rows" :2,
                "arrows": true
                }' data-slick-responsive='[
                {"breakpoint":991, "settings": {"slidesToShow": 3} },
                {"breakpoint":768, "settings": {"slidesToShow": 2} },
                {"breakpoint":480, "settings": {"slidesToShow": 1,"rows" :1} }
                ]'>
            <c:forEach items="${listA}" var="t">
                <div class="single-slide">
                    <div class="pm-product">
                        <div class="image">
                            <a href="detail?pID=${t.id}"><img src="${t.image}" alt=""></a>
                            <div class="hover-conents">
                                <ul class="product-btns">
                                    <li><a href="#"><i class="ion-ios-heart-outline"></i></a></li>
                                    <li><a href="#"><i class="ion-ios-shuffle"></i></a></li>
                                </ul>
                            </div>
                            <span class="onsale-badge">Sale!</span>
                        </div>
                        <div class="content">
                            <h3>${t.name}</h3>
                            <div class="price text-orange">

                                <span>${t.price} VND</span>
                            </div>
                            <div class="btn-block">
                                <a href="cart-home?&id=${t.id}" class="btn btn-outlined btn-rounded btn-mid">Thêm vào
                                    giỏ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </div>
</div>
<!-- Home =>  Promotion Block 2 -->
<section class="pt--50 space-db--30">
    <h2 class="d-none">Promotion Block
    </h2>
    <div class="container">
        <a class="promo-image overflow-image">
            <img src="" alt="">
        </a>
    </div>
</section>
<!-- slide Block 3 / Normal Slider -->
<div class="pt--50">
    <div class="container">
        <div class="block-title-2">
            <h2>SẢN PHẨM BÁN CHẠY TRONG TUẦN</h2>
        </div>
        <div class="petmark-slick-slider petmark-slick-slider--wrapper-2 border normal-slider"
             data-slick-setting='{
                "autoplay": true,
                "autoplaySpeed": 3000,
                "slidesToShow": 3,
                "arrows": true
                }' data-slick-responsive='[{"breakpoint":991, "settings": {"slidesToShow": 2} },
                {"breakpoint":768, "settings": {"slidesToShow": 1}
                }]'>
            <c:forEach items="${listP}" var="p">
                <div class="single-slide">
                    <div class="pm-product  product-type-list">
                        <div class="image">
                            <a href="detail?pID=${p.id}"><img src="${p.image}" alt=""></a>
                            <span class="onsale-badge">Sale!</span>
                        </div>
                        <div class="content">
                            <h3><a href="product-details.html">${p.name}</a></h3>
                            <div class="price text-orange">
                                <span>${p.price} VND</span>
                            </div>
                            <div class="btn-block">
                                <a href="cart-home?&id=${p.id}" class="btn btn-outlined btn-rounded btn-mid">Thêm vào
                                    giỏ</a>
                            </div>
                            <div class="rating-widget mt--20">
                                <a href="#" class="single-rating"><i class="fas fa-star"></i></a>
                                <a href="#" class="single-rating"><i class="fas fa-star"></i></a>
                                <a href="#" class="single-rating"><i class="fas fa-star"></i></a>
                                <a href="#" class="single-rating"><i class="fas fa-star-half-alt"></i></a>
                                <a href="#" class="single-rating"><i class="far fa-star"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<!-- slide Block 3 / Normal Slider -->
<div class="pt--50 pb--50">
    <div class="container">
        <div class="petmark-slick-slider brand-slider  border normal-slider grid-border-none"
             data-slick-setting='{
                "autoplay": true,
                "autoplaySpeed": 3000,
                "slidesToShow": 5,
                "arrows": true
                }' data-slick-responsive='[
                {"breakpoint":991, "settings": {"slidesToShow": 4} },
                {"breakpoint":768, "settings": {"slidesToShow": 3} },
                {"breakpoint":480, "settings": {"slidesToShow": 2} },
                {"breakpoint":320, "settings": {"slidesToShow": 1} }
                ]'>
        </div>
    </div>
</div>
<!-- Modal -->

<script>
    // Hàm điều hướng đến trang profile.jsp và tab "Quản lý khóa"
    function navigateToKeyManagement() {
        // Chuyển hướng đến profile.jsp và thêm query string để xác định tab
        window.location.href = 'profile.jsp';
    }

    var isLoggedIn = <%= session.getAttribute("user") != null %>;

    function pollForDataChange() {
        if (isLoggedIn) {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        var responseText = xhr.responseText;
                        console.log(responseText);
                        if (responseText === "false") {
                            alert("Dữ liệu hóa đơn của bạn đã bị thay đổi");
                        } else if (responseText === "no_data") {
                            /// tùy chinh code
                        }
                    } else if (xhr.status === 403) {
                        // Key bị hủy, dừng việc polling
                        console.log("Key của người dùng đã bị hủy. Dừng polling.");
                        return;
                    }

                    // Chờ 10 giây và tiếp tục polling
                    setTimeout(pollForDataChange, 10000);
                }
            };
            xhr.open("GET", "/ATBMHTTT_war/PollingServlet", true);
            xhr.send();
        } else {
            // User is not logged in, you may want to handle this case accordingly
            console.log("User is not logged in. Polling aborted.");
        }
    }

    // Run the function when the page is loaded
    pollForDataChange();


</script>

<jsp:include page="footer/footer.jsp"></jsp:include>
<script src="js/plugins.js"></script>
<script src="js/ajax-mail.js"></script>
<script src="js/custom.js"></script>
</body>
</html>
