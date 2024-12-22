package control;

import dao.CreateKeyDAO;
import dao.DAO;
import entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.PublicKey;

@WebServlet(name = "KeyManagementServlet", value = "/KeyManagementServlet")
public class KeyManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String publicKey = request.getParameter("publicKey");
        HttpSession session = request.getSession();
        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        int uId = Integer.parseInt(user.getId());
        CreateKeyDAO dao = new CreateKeyDAO();
        boolean keyExists = dao.checkKey(uId);

        if (user != null) {
            switch (action) {
                case "revokeKey":
                    if (keyExists) {
                        dao.reportLostKey(uId);
                        response.getWriter().write("Yêu cầu hủy key của bạn đã được xử lý");
                    } else {
                        response.getWriter().write("Yêu cầu hủy key của bạn không thành công");
                    }
                    break;
                case "generateNewKey":
                    if (!keyExists && publicKey != null && !publicKey.isEmpty()) {
                        dao.create_key(publicKey, uId);
                        // Cập nhật session sau khi tạo key thành công
                        session.setAttribute("keyExists", true);
                        response.getWriter().write("Yêu cầu tạo key mới của bạn đã được xử lý thành công.");
                    } else {
                        response.getWriter().write("Yêu cầu tạo key mới của bạn không thành công. Key đã tồn tại hoặc dữ liệu không hợp lệ.");
                    }
                    break;

                default:
                    response.getWriter().write("Hành động không hợp lệ");
            }
        } else {
            response.sendRedirect("login");
        }
    }
}
