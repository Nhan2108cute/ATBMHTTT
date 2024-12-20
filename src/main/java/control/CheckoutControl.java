package control;

import dao.BillDAO;
import dao.CartDAO;
import dao.CartDB_DAO;
import dao.DAO;
import entity.*;
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
    private CartDB_DAO cartDAO = new CartDB_DAO();


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
        User user = (User) session.getAttribute("user");

        try {
            if (user != null) {
                // Tổng tổng giỏ hàng trong database
                List<Cart> cartItems = cartDAO.getCartByUserId(Integer.parseInt(user.getId()));
                if (cartItems.isEmpty()) {
                    request.setAttribute("message", "Giỏ hàng trống!");
                    request.getRequestDispatcher("/checkout.jsp").forward(request, response);
                    return;
                }

                // Tính tổng tiền
                double subtotal = cartDAO.getCartTotal(Integer.parseInt(user.getId()));
                int shippingFee = subtotal > 0 ? 35000 : 0;
                double total = subtotal + shippingFee;

                // Tạo mã Hash cho dữ liệu đơn hàng
                String data = ten + " " + dia_chi_giao_hang + " " + pt_thanhtoan + " " + ghichu;
                String hashValue = SHA.hash(data);

                // Lưu Bill vào database với trường hash
                Bill bill = new Bill(
                        0,  // ID tu động tăng
                        user,
                        ten,
                        new Timestamp(System.currentTimeMillis()),
                        dia_chi_giao_hang,
                        pt_thanhtoan,
                        ghichu,
                        total,
                        hashValue,
                        ""  // Signature để trống
                );

                // luu hoa đơn
                int billId = billDAO.addBill(bill);

                // lưu vao chi tiet hoa don
                for (Cart cartItem : cartItems) {
                    BillDetails billDetails = new BillDetails(
                            billId,
                            cartItem.getProduct(),
                            cartItem.getQuantity(),
                            cartItem.getPrice()
                    );
                    billDAO.addBillDetails(billDetails);
                }

                //xóa gio hang của người dùng sau khi thanh toan thành công
                cartDAO.clearCart(Integer.parseInt(user.getId()));

                request.setAttribute("message", "Đặt hàng thành công!");
                // Chuyển hướng về trang chủ
                response.sendRedirect("home?message=order_success");

            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra khi đặt hàng!");
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
        }
    }
}
