<%@ page import="entity.Bill" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Đơn Hàng</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .edit-order-form {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-title {
            font-size: 26px;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
            color: #333;
        }
        .btn-custom {
            background-color: #007bff;
            border: none;
        }
        .btn-custom:hover {
            background-color: #0056b3;
        }
        .btn-cancel {
            background-color: #6c757d;
            border: none;
        }
        .btn-cancel:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<%
    Bill bill = (Bill) request.getAttribute("bill");
    if (bill == null) {
%>
<div class="alert alert-danger text-center" role="alert">
    Không tìm thấy thông tin hóa đơn.
</div>
<%
        return;
    }
%>

<div class="edit-order-form">
    <h2 class="form-title">Chỉnh Sửa Đơn Hàng</h2>
    <form action="edit-order" method="POST">
        <!-- ID Đơn hàng -->
        <input type="hidden" name="id" value="<%= bill.getId() %>">

        <!-- Tên khách hàng -->
        <div class="mb-3">
            <label for="customerName" class="form-label">Tên khách hàng</label>
            <input type="text" class="form-control" id="customerName" name="ten" value="<%= bill.getTen() %>" placeholder="Nhập tên khách hàng" required>
        </div>

        <!-- Địa chỉ giao hàng -->
        <div class="mb-3">
            <label for="address" class="form-label">Địa chỉ giao hàng</label>
            <input type="text" class="form-control" id="address" name="diachi" value="<%= bill.getDiachi() %>" placeholder="Nhập địa chỉ giao hàng" required>
        </div>

        <!-- Ghi chú -->
        <div class="mb-3">
            <label for="note" class="form-label">Ghi chú</label>
            <textarea class="form-control" id="note" name="ghichu" rows="3" placeholder="Nhập ghi chú"><%= bill.getGhiChu() %></textarea>
        </div>

        <!-- Tổng tiền -->
        <div class="mb-3">
            <label for="totalAmount" class="form-label">Tổng tiền</label>
            <input type="number" class="form-control" id="totalAmount" name="tongtien" value="<%= bill.getTongTien() %>" step="0.01" placeholder="Nhập tổng tiền" required>
        </div>

        <!-- Phương thức thanh toán -->
        <div class="mb-3">
            <label for="paymentMethod" class="form-label">Phương thức thanh toán</label>
            <select class="form-select" id="paymentMethod" name="pt_thanhtoan" required>
                <option value="Thanh toan khi nhan hang" <%= bill.getPt_thanhToan().equals("Thanh toan khi nhan hang") ? "selected" : "" %>>Thanh toán khi nhận hàng</option>

            </select>
        </div>

        <!-- Trạng thái -->
        <div class="mb-3">
            <label for="status" class="form-label">Trạng thái</label>
            <select class="form-select" id="status" name="status" required>
                <option value="Chua xac thuc" <%= bill.getStatus().equals("Chua xac thuc") ? "selected" : "" %>>Chưa Xác Thực</option>
                <option value="Da xac thuc" <%= bill.getStatus().equals("Da xac thuc") ? "selected" : "" %>>Đã Xác Thực</option>
                <option value="Huy" <%= bill.getStatus().equals("Huy") ? "selected" : "" %>>Hủy</option>
            </select>
        </div>

        <!-- Buttons -->
        <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-custom">Lưu</button>
            <a href="/order-control" class="btn btn-cancel">Hủy</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>
