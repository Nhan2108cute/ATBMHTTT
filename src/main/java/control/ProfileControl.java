package control;

import dao.BillDAO;
import dao.DAO;
import entity.Bill;
import entity.User;
import services.EmailService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "profile", value = "/profile")
public class ProfileControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session1 = request.getSession();
        Object userObj = session1.getAttribute("user");
        if (userObj != null) {
            User user = (User) userObj;
            String userId = user.getId();

            BillDAO billDAO = new BillDAO();
            List<Bill> billList = billDAO.getBillDetails(userId);

            // Kiểm tra thay đổi hóa đơn
            List<Bill> previousBillList = (List<Bill>) session1.getAttribute("userBills");
            if (previousBillList != null && !billList.equals(previousBillList)) {
                // Gửi email thông báo thay đổi hóa đơn
                sendBillChangeNotification(user, billList);
            }

            // Lưu danh sách hóa đơn mới vào session
            session1.setAttribute("userBills", billList);
        }
        HttpSession session = request.getSession();
        Object o = session.getAttribute("user");
        if (o == null) {
            response.sendRedirect("login");
        } else {
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        HttpSession session = request.getSession();
        Object o = session.getAttribute("user");
        User u = (User) o;

        DAO dao = new DAO();
        dao.changeProfile(fullname, email, username, phone, address, u.getId());
        request.setAttribute("mess", "thanh cong");
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    private void sendBillChangeNotification(User user, List<Bill> updatedBills) {
        String recipientEmail = user.getEmail();
        String subject = "Thông báo thay đổi hóa đơn";
        StringBuilder body = new StringBuilder();
        body.append("Xin chào ").append(user.getFullName()).append(",\n\n");
        body.append("Hóa đơn của bạn đã có sự thay đổi. Vui lòng kiểm tra thông tin chi tiết như sau:\n\n");

        for (Bill bill : updatedBills) {
            body.append("Mã hoá đỡn: ").append(bill.getId()).append("\n")
                    .append("Thời gian đặt hàng: ").append(bill.getNgayLap_hoaDon()).append("\n")
                    .append("Lần cuối thay đổi: ").append(bill.getLancuoithaydoi_hoaDon()).append("\n")
                    .append("Tên người nhận: ").append(bill.getTen()).append("\n")
                    .append("Địa chỉ: ").append(bill.getDiachi()).append("\n")
                    .append("Tổng tiền: ").append(bill.getTongTien()).append("\n")
                    .append("Ghi chú: ").append(bill.getGhiChu()).append("\n")
                    .append("Trạng thái: ").append(bill.getStatus()).append("\n\n");
        }

        body.append("Vui lòng đăng nhập vào hệ thống để kiểm tra thêm thông tin chi tiết.\n");
        body.append("Trân trọng.");

        EmailService emailService = new EmailService();
        emailService.sendEmail(recipientEmail, subject, body.toString());
    }
}
