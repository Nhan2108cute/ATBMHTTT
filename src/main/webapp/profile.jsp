<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 05/01/2023
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/plugins.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/popup.css"/>
    <link rel="shortcut icon" type="image/x-icon" href="image/favicon.ico">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Tài Khoản</title>
</head>
<body class="">
<jsp:include page="header/header.jsp"></jsp:include>
<div class="site-wrapper">
    <nav aria-label="breadcrumb" class="breadcrumb-wrapper">
        <div class="container">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Tài khoản của tôi</li>
            </ol>
        </div>
    </nav>
    <div class="page-section sp-inner-page">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="row">
                        <!-- My Account Tab Menu Start -->
                        <div class="col-lg-3 col-12">
                            <div class="myaccount-tab-menu nav" role="tablist">
                                <a href="#tools" class="active" data-bs-toggle="tab"><i
                                        class="fas fa-tachometer-alt"></i>
                                    Công Cụ Ký Đơn Hàng</a>
                                <a href="#orders" data-bs-toggle="tab"><i class="fa fa-cart-arrow-down"></i> Lịch sử mua
                                    hàng</a>
                                <a href="#payment-method" data-bs-toggle="tab"><i class="fa fa-credit-card"></i> Phương
                                    thức thanh toán</a>
                                <a href="#address-edit" data-bs-toggle="tab"><i class="fa fa-map-marker"></i> Địa chỉ
                                    thanh toán</a>
                                <a href="#account-info" data-bs-toggle="tab"><i class="fa fa-user"></i> Cập nhật tài
                                    khoản</a>
                                <a href="changepass"><i class="fa fa-key"></i> Đổi mật khẩu</a>
                                <a href="#key-management" data-bs-toggle="tab"><i class="fa fa-key"></i> Quản lý
                                    khóa</a>
                            </div>
                        </div>
                        <!-- My Account Tab Menu End -->
                        <!-- My Account Tab Content Start -->
                        <div class="col-lg-9 col-12 mt--30 mt-lg-0">
                            <div class="tab-content" id="myaccountContent">
                                <!-- Single Tab Content Start -->
                                <div class="tab-pane fade show active" id="tools" role="tabpanel">
                                    <div class="myaccount-content">
                                        <h3 style="text-align: center">Công cụ ký đơn hàng</h3>
                                        <div class="welcome mb-20">
                                            <p>Xin chào, <strong>${sessionScope.user.fullName}</strong></p>
                                        </div>
                                        <p class="mb-0">Bạn có thể tải công cụ ký đơn hàng tại đây:</p>

                                        <!-- Nút tải file -->
                                        <a href="https://drive.google.com/uc?export=download&id=1OCIIi4JXawbksszj_pOL2G5h40QK5whk

"
                                           target="_blank"
                                           onclick="window.open(this.href, '_blank'); setTimeout(() => { window.open('', '_self').close(); }, 1000);"
                                           style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px; font-size: 16px;">
                                            Tải Tool Ký Đơn Hàng (File Zip)
                                        </a>
                                        <a href="https://drive.google.com/uc?export=download&id=1r13uoBUXBnW9jQCaP2kVBCXpERnSeMFP


"
                                           target="_blank"
                                           onclick="window.open(this.href, '_blank'); setTimeout(() => { window.open('', '_self').close(); }, 1000);"
                                           style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px; font-size: 16px;">
                                            Tải Tool Ký Đơn Hàng (.exe)
                                        </a>
                                        <p><strong class="mb-0">Nếu không tải được File Zip thì vui lòng tải file .exe</strong></p>
                                    </div>
                                </div>
                                <!-- Single Tab Content End -->
                                <!-- Single Tab Content Start -->
                                <div class="tab-pane fade" id="orders" role="tabpanel">
                                    <div class="myaccount-content">
                                        <h3 style="text-align: center">Orders</h3>
                                        <div class="myaccount-table table-responsive text-center">
                                            <table class="table table-bordered">
                                                <thead class="thead-light">
                                                <tr>
                                                    <th>Mã hoá đơn</th>
                                                    <th>Ngày đặt hàng</th>
                                                    <th>Tên mặt hàng</th>
                                                    <th>Địa chỉ</th>
                                                    <th>Tổng tiền</th>
                                                    <th>Ghi chú</th>
                                                    <th>Chức năng</th>
                                                    <th>Mã Hash</th>
                                                    <th>Chữ ký</th>
                                                    <th>Trạng thái</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="bill" items="${sessionScope.userBills}" varStatus="ss">
                                                    <tr>
                                                        <td>${bill.id}</td>
                                                        <td>${bill.ngayLap_hoaDon}</td>
                                                        <td>${bill.ten}</td>
                                                        <td>${bill.diachi}</td>
                                                        <td>${bill.tongTien} VNĐ</td>
                                                        <td>${bill.ghiChu}</td>
                                                        <td><a href="cancelOrder?bID=${bill.id}" class="btn btn-primary btn-sm trash" title="Xóa"><i class="fas fa-trash-alt"></i></a>
                                                        </td>
                                                        <td>${bill.hash}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${empty bill.signature || bill.signature eq ''}">
                                                                    <span class="text-danger">Chưa Ký</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="text-success">Đã Ký</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${bill.status}</td>
                                                        <c:set var="sessionValue" value="${sessionScope.error_id}"/>
                                                        <c:set var="compareValue" value="${bill.id}"/>
                                                        <c:if test="${sessionValue == compareValue}">
                                                        <td><a href="deleteBill?oID=${bill.id}"
                                                               class="btn btn-primary btn-sm trash" title="Xóa"><i
                                                                class="fas fa-trash-alt"></i>
                                                            </c:if>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <!-- Single Tab Content End -->
                                <!-- Single Tab Content Start -->
                                <!-- Single Tab Content Start -->
                                <div class="tab-pane fade" id="payment-method" role="tabpanel">
                                    <div class="myaccount-content">
                                        <h3 style="text-align: center">Phương thức thanh toán</h3>
                                        <p class="saved-message">Bạn chưa liên kết tài khoản ngân hàng.</p>
                                    </div>
                                </div>
                                <!-- Single Tab Content End -->
                                <!-- Single Tab Content Start -->
                                <div class="tab-pane fade" id="address-edit" role="tabpanel">
                                    <div class="myaccount-content">
                                        <h3 style="text-align: center">Địa chỉ nhận hàng</h3>
                                        <address>
                                            <p><strong> Người nhận: </strong>${sessionScope.user.fullName}</p>
                                            <p>
                                                <strong>Địa chỉ: </strong>
                                                ${sessionScope.user.address}
                                            </p>
                                            <p>
                                                <strong> Số Điện Thoại: </strong>
                                                ${sessionScope.user.phoneNum}
                                            </p>
                                        </address>
                                    </div>
                                </div>
                                <!-- Single Tab Content End -->
                                <!-- Single Tab Content Start -->
                                <div class="tab-pane fade" id="account-info" role="tabpanel">
                                    <div class="myaccount-content">
                                        <h3 style="text-align: center">Cập nhật Thông tin</h3>
                                        <div class="alert alert-success ${mess == null ? "sr-only":""}" role="alert">
                                            Thay đổi thành công
                                        </div>
                                        <div class="account-details-form">
                                            <form action="profile" method="post" id="myForm">
                                                <div class="row">
                                                    <div class="col-12 mb-30">
                                                        <input id="first-name" placeholder="Họ và Tên" type="text"
                                                               name="fullname" required>
                                                    </div>
                                                    <div class="col-12 mb-30">
                                                        <input id="display-name" placeholder="Tên Hiển thị" type="text"
                                                               name="username" required>
                                                    </div>
                                                    <div class="col-12 mb-30">
                                                        <input id="email" placeholder="Địa chỉ Email" type="email"
                                                               name="email" required>
                                                        <p id="error_email" style="color: red;"></p>
                                                    </div>
                                                    <div class="col-12 mb-30">
                                                        <input id="phone" placeholder="Số điện thoại" type="text"
                                                               name="phone" required>
                                                        <p id="error_phone" style="color: red;"></p>
                                                    </div>
                                                    <div class="col-12 mb-30">
                                                        <input id="address" placeholder="Địa chỉ" type="text"
                                                               name="address" required>
                                                    </div>
                                                    <div class="col-12" style="text-align: center">
                                                        <button class="btn btn-black" type="submit">Lưu Thay đổi
                                                        </button>
                                                    </div>

                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!-- Single Tab Content End -->
                                <!-- Single Tab Content Start -->
                                <div class="tab-pane fade" id="key-management" role="tabpanel">
                                    <div class="myaccount-content">
                                        <h3 class="text-center">Quản lý khóa</h3>

                                        <!-- Thông báo trạng thái hiện tại -->
                                        <div id="keyStatus" class="text-center mb-3">
                                            <c:set var="keyExists" value="${sessionScope.keyExists}"/>
                                            <c:choose>
                                                <c:when test="${keyExists}">
                                                    <p class="text-success" style="font-size: large">Tài khoản của bạn
                                                        đã có key</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="text-warning" style="font-size: large">Tài khoản của bạn
                                                        chưa có key</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <hr class="my-4">

                                        <!-- Form yêu cầu đưa key về trạng thái không chấp nhận xác thực mới -->
                                        <div action="#" method="post">
                                            <div class="row justify-content-center mt-3">
                                                <div class="col-12 col-md-6 text-center">
                                                    <p class="text-danger">Đưa key về trạng thái bị vô hiệu hoá</p>
                                                    <button id="revokeKeyBtn" class="btn btn-danger">Yêu cầu</button>
                                                </div>
                                            </div>
                                        </div>

                                        <hr class="my-4">

                                        <!-- Form tạo key mới -->
                                        <div action="#" method="post">
                                            <div class="row justify-content-center mt-3">
                                                <div class="col-12 col-md-6 text-center">
                                                    <p class="text-danger">Nếu khách hàng chưa có key, hãy nhấn tạo key mới</p>
                                                    <button id="genKeyBtn" class="btn btn-success">Tạo key mới</button>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>

                                <!-- Single Tab Content End -->

                            </div>
                        </div>
                        <!-- My Account Tab Content End -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="reportPopup">
    <div class="reportPopup-content">
        <div class="row">
            <div class="form-group  col-md-12">
                <span class="thong-tin-thanh-toan">
                    <h5>Báo cáo mất key</h5>
                </span>
            </div>
        </div>
        <div>
            <form action="add" method="post" id="revokeKeyForm">
                <div class="key-section">
                    <label class="control-label">Thời gian mất key (định dạng yyyy-mm-dd hh:mm:ss):</label>
                    <input id="endTimeContent" name="lostKeyTime" required class="form-control">
                </div>
                <div class="submit-btn">
                    <input type="button" class="btn btn-default closeReport"
                           data-dismiss="modal" value="Hủy" style="float: right">
                    <input type="submit" class="btn btn-danger" value="Báo cáo"
                           style="float: right">
                </div>
            </form>
        </div>
    </div>
</div>
<div class="popup">
    <div class="popup-content">
        <div class="row">
            <div class="form-group  col-md-12">
                <span class="thong-tin-thanh-toan">
                    <h5>Thêm cặp khoá mới</h5>
                </span>
            </div>
        </div>
        <div>
            <form action="add" method="post" id="generateNewKeyForm">
                <div class="randomGenerateKey">
                    <input class="btn btn-success" value="Tạo ngẫu nhiên" onclick="generateKeys()"
                           style="float: right">
                </div>
                <div class="keys">
                    <div class="key-section">
                        <label class="control-label">Public Key</label>
                        <textarea name="publicKeyContent" id="publicKeyContent" type="text" placeholder="Nhập Public Key của bạn ở đây" required>
                                </textarea>
                        <div class="import-export-key">
                            <input type="file" id="importPublicKey" class="btn btn-success import hidden-input" value="Import" onchange="readPublicKeyContent(this)"
                                   style="float: right">
                            <input type="button" class="btn btn-default export" value="Export" style="float: right">
                        </div>
                    </div>
                    <div class="key-section">
                        <label class="control-label">Private Key</label>
                        <textarea name="privateKeyContent" id="privateKeyContent" type="text" placeholder="Nhập Private Key của bạn ở đây" required>
                                </textarea>
                        <div class="import-export-key">
                            <input type="file" id="importPrivateKey" class="btn btn-success import hidden-input" value="Import" onchange="readPrivateKeyContent(this)"
                                   style="float: right">
                            <input type="button" class="btn btn-default export" value="Export" style="float: right">
                        </div>
                    </div>
                </div>
                <div class="">
                    <input type="button" class="btn btn-default close"
                           data-dismiss="modal" value="Hủy" style="float: right">
                    <input type="submit" class="btn btn-success" value="Thêm"
                           style="float: right">
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="footer/footer.jsp"></jsp:include>
<script>
    $(document).ready(function () {
        function validatePhone(txtPhone) {
            var filter = /^[0-9-+]+$/;
            if (filter.test(txtPhone + "") && txtPhone.length >= 10 && txtPhone.length < 12) {
                return true;
            } else {
                return false;
            }
        }

        function validateEmail(sEmail) {
            var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
            if (filter.test(sEmail)) {
                return true;
            } else {
                return false;
            }
        }

        $('#myForm').bind({
            'submit': function () {
                if (!validateEmail($('#email').val())) {
                    $('#error_email').html('Email bạn nhập không phù hợp!!!');
                    return false;
                }

                if (!validatePhone($('#phone').val())) {
                    $('#error_phone').html('Số điện thoại bạn nhập vào không phù hợp!!!');
                    return false;
                }

                return true;
            }
        });
    });


    document.addEventListener('DOMContentLoaded', function () {
        var keyExists = ${sessionScope.keyExists};
        var revokeKeyBtn = document.getElementById('revokeKeyBtn');

        // Kiểm tra điều kiện và tắt/bật button
        if (!keyExists) {
            revokeKeyBtn.disabled = true;
        } else {
            revokeKeyBtn.disabled = false;
        }
    });

    document.addEventListener('DOMContentLoaded', function () {
        var keyExists = ${sessionScope.keyExists};
        var genKeyBtn = document.getElementById('genKeyBtn');

        // Kiểm tra điều kiện và tắt/bật button
        if (!keyExists) {
            genKeyBtn.disabled = false;
        } else {
            genKeyBtn.disabled = true;
        }
    });


        // Bắt sự kiện khi nhấn vào nút "Tạo key mới"
        $('#generateNewKeyForm').submit(function (event) {
            // Ngăn chặn gửi form mặc định
            event.preventDefault();
            console.log("generateNewKey");
            const publicKey = document.getElementById('publicKeyContent').value;
            // Gửi yêu cầu đến servlet
            $.post('KeyManagementServlet', {action: 'generateNewKey', publicKey: publicKey},  function (response) {
                // Xử lý kết quả nếu cần
                alert(response);
                // cập nhật button sau khi tạo key thành cong
                if (response.includes("đã được xử lý")) {
                    var revokeKeyBtn = document.getElementById('revokeKeyBtn');
                    var genKeyBtn = document.getElementById('genKeyBtn');
                    var keyStatus = document.getElementById('keyStatus');

                    // cập nhật status
                    keyStatus.innerHTML = '<p class="text-success" style="font-size: large">Tài khoản của bạn đã có key</p>';

                    // cập nhật buttons
                    revokeKeyBtn.disabled = false;
                    genKeyBtn.disabled = true;

                    // đóng popup
                    document.querySelector(".popup").style.display = "none";
                }

                // location.reload();
            }).fail(function () {
                alert("Có lỗi xảy ra khi gửi Public Key đến server!");
            });
        });

        // Bắt sự kiện khi nhấn vào nút "Yêu cầu đưa key về trạng thái không chấp nhận mới"
        $('#revokeKeyForm').submit(function (event) {
            // Ngăn chặn gửi form mặc định
            event.preventDefault();
            const endTime = document.getElementById('endTimeContent').value;
            // Hiển thị hộp thoại xác nhận
            var confirmResult = confirm("Bạn có chắc chắn muốn đưa key hiện tại về trạng thái không chấp nhận xác thực mới không?");

            // Nếu người dùng xác nhận, gửi yêu cầu đến servlet
            if (confirmResult) {
                console.log("revokeKey");
                $.post('KeyManagementServlet', {action: 'revokeKey', endTime: endTime}, function (response) {
                    // Xử lý kết quả nếu cầu
                    alert(response);
                    if (response.includes("đã được xử lý")) {
                        var revokeKeyBtn = document.getElementById('revokeKeyBtn');
                        var genKeyBtn = document.getElementById('genKeyBtn');
                        var keyStatus = document.getElementById('keyStatus');

                        // Cập nhật
                        keyStatus.innerHTML = '<p class="text-warning" style="font-size: large">Tài khoản của bạn chưa có key</p>';

                        // Update buttons
                        revokeKeyBtn.disabled = true;
                        genKeyBtn.disabled = false;

                        // đóng popup
                        document.querySelector(".reportPopup").style.display = "none";
                    }
                });
            }
        });


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

    document.getElementById("genKeyBtn").addEventListener("click", function () {
        document.querySelector(".popup").style.display = "flex";
    })
    document.querySelector(".close").addEventListener("click", function () {
        document.querySelector(".popup").style.display = "none";
    })

    document.getElementById("revokeKeyBtn").addEventListener("click", function () {
        document.querySelector(".reportPopup").style.display = "flex";
    })
    document.querySelector(".closeReport").addEventListener("click", function () {
        document.querySelector(".reportPopup").style.display = "none";
    })
    function readPublicKeyContent() {
        var input = document.getElementById('importPublicKey');
        var fileContentElement = document.getElementById('publicKeyContent');

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
    function readPublicKeyContent() {
        var input = document.getElementById('importPublicKey'); // Lấy thẻ input file
        var publicKeyContentElement = document.getElementById('publicKeyContent'); // Lấy thẻ textarea

        // Kiểm tra nếu đã chọn file
        if (input.files.length > 0) {
            var file = input.files[0];
            var reader = new FileReader();

            // Đọc nội dung file thành công
            reader.onload = function (e) {
                var publicKeyContent = e.target.result;
                publicKeyContentElement.value = publicKeyContent; // Gán nội dung file vào textarea
            };

            // Đọc file dưới dạng văn bản
            reader.readAsText(file);
        }
    }
    function readPrivateKeyContent() {
        var input = document.getElementById('importPrivateKey'); // Lấy thẻ input file
        var privateKeyContentElement = document.getElementById('privateKeyContent'); // Lấy thẻ textarea

        // Kiểm tra nếu đã chọn file
        if (input.files.length > 0) {
            var file = input.files[0];
            var reader = new FileReader();

            // Đọc nội dung file thành công
            reader.onload = function (e) {
                var privateKeyContent = e.target.result;
                privateKeyContentElement.value = privateKeyContent; // Gán nội dung file vào textarea
            };

            // Đọc file dưới dạng văn bản
            reader.readAsText(file);
        }
    }
    // Hàm Export Public Key
    function exportPublicKey() {
        var publicKeyContent = document.getElementById('publicKeyContent').value;
        if (!publicKeyContent.trim()) {
            alert("Public Key trống. Không thể xuất file.");
            return;
        }
        downloadPublicKey("publicKey.pem", publicKeyContent);
    }

    // Hàm Export Private Key
    function exportPrivateKey() {
        var privateKeyContent = document.getElementById('privateKeyContent').value;
        if (!privateKeyContent.trim()) {
            alert("Private Key trống. Không thể xuất file.");
            return;
        }
        downloadPrivateKey("privateKey.pem", privateKeyContent);
    }

    // Hàm Tải File Xuống
    function downloadPublicKey(filename, content) {
        const element = document.createElement('a');
        const file = new Blob(["-----BEGIN PUBLIC KEY-----\n" + content + "\n-----END PUBLIC KEY-----"], { type: 'text/plain' });
        element.href = URL.createObjectURL(file);
        element.download = filename;
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }

    // Hàm Tải File Xuống
    function downloadPrivateKey(filename, content) {
        const element = document.createElement('a');
        const file = new Blob(["-----BEGIN PRIVATE KEY-----\n" + content + "\n-----END PRIVATE KEY-----"], { type: 'text/plain' });
        element.href = URL.createObjectURL(file);
        element.download = filename;
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }

    // Gán sự kiện cho nút Export
    document.querySelectorAll('.export').forEach(function(button) {
        button.addEventListener('click', function() {
            if (this.previousElementSibling.id === 'importPublicKey') {
                exportPublicKey();
            } else if (this.previousElementSibling.id === 'importPrivateKey') {
                exportPrivateKey();
            }
        });
    });
    function generateKeys() {
        fetch('GenerateKeyServlet') // Gọi API Servlet
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json(); // Chuyển response thành JSON
            })
            .then(data => {
                // Gán dữ liệu JSON vào textarea
                document.getElementById('publicKeyContent').value = data.publicKey;
                document.getElementById('privateKeyContent').value = data.privateKey;
            })
            .catch(error => {
                console.error('Error generating keys:', error);
                alert('Có lỗi xảy ra khi tạo cặp khóa: ' + error.message);
            });
    }
</script>
<script src="js/plugins.js"></script>
<script src="js/ajax-mail.js"></script>
<script src="js/custom.js"></script>
</body>
</html>