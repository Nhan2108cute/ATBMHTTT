package control;

import dao.BillDAO;
import dao.CartDAO;
import dao.DAO;
import entity.*;
import services.EmailService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static entity.ElectronicSignatureVerification.*;

@WebServlet(name = "CheckoutControl", value = "/CheckoutControl")
public class CheckoutControl extends HttpServlet {
    private BillDAO billDAO = new BillDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            request.getRequestDispatcher("checkout").forward(request, response);
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String ten = request.getParameter("ten");
        String dia_chi_giao_hang = request.getParameter("dia_chi_giao_hang");
        String pt_thanhtoan = request.getParameter("pt_thanhtoan");
        String ghichu = request.getParameter("ghichu");

        List<Product> list = CartDAO.getGiohang();
        User user = (User) session.getAttribute("user");

        try {
            if (user != null) {
                Date date = new Date();
                double total = 0;
                int shippingFee = 0;

                // Tính tổng giá trị đơn hàng
                HashMap<Product, Integer> map = new HashMap<>();
                for (Product p : list) {
                    total += p.getPrice();
                    map.put(p, map.getOrDefault(p, 0) + 1);
                }

                if (total > 0) shippingFee = 35000;

                // Tạo mã Hash cho dữ liệu đơn hàng
                String data = ten + " " + dia_chi_giao_hang + " " + pt_thanhtoan + " " + ghichu;
                String hashValue = SHA.hash(data);

                // Lưu Bill vào database với trường hash

                Bill bill = new Bill(0, user, ten, new Timestamp(date.getTime()), new Timestamp(date.getTime()), dia_chi_giao_hang, pt_thanhtoan, ghichu, total + shippingFee, hashValue, "", "Chua xac thuc");

                int idBill = billDAO.addBill(bill);

                // Lưu chi tiết đơn hàng
                for (Map.Entry<Product, Integer> entry : map.entrySet()) {
                    Product product = entry.getKey();
                    int soLuong = entry.getValue();
                    billDAO.addBillDetails(new BillDetails(idBill, product, soLuong, product.getPrice()));
                }

                // Gửi email thông báo đặt hàng thành công
                sendOrderConfirmationEmail(user, bill, map);

                // Xóa giỏ hàng sau khi đặt hàng thành công
                list.clear();
                request.setAttribute("message", "Đặt hàng thành công!");

                // Chuyển hướng đến trang checkout.jsp
                RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra khi đặt hàng!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void sendOrderConfirmationEmail(User user, Bill bill, HashMap<Product, Integer> productMap) {
        String recipientEmail = user.getEmail();
        String subject = "Xác nhận đặt hàng thành công";
        StringBuilder body = new StringBuilder();

        body.append("Xin chào ").append(user.getFullName()).append(",\n\n");
        body.append("Bạn đã đặt hàng thành công với các thông tin như sau:\n");
        body.append("Mã hóa đơn: ").append(bill.getId()).append("\n");
        body.append("Tên người nhận: ").append(bill.getTen()).append("\n");
        body.append("Địa chỉ giao hàng: ").append(bill.getDiachi()).append("\n");
        body.append("Phương thức thanh toán: ").append(bill.getPt_thanhToan()).append("\n");
        body.append("Ghi chú: ").append(bill.getGhiChu()).append("\n");
        body.append("Tổng tiền: ").append(bill.getTongTien()).append(" VNĐ\n");
        body.append("\nChi tiết đơn hàng:\n");

        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            body.append("- ").append(product.getName()).append(": ").append(quantity).append(" x ").append(product.getPrice()).append(" VNĐ\n");
        }

        body.append("\nCảm ơn bạn đã mua sắm tại cửa hàng của chúng tôi!\n");
        body.append("Trân trọng.");

        // Gửi email
        EmailService emailService = new EmailService();
        emailService.sendEmail(recipientEmail, subject, body.toString());
    }
}
