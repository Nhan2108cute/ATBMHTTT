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
        Object o = session.getAttribute("user");
        User user = (User) o;
        int uId = Integer.parseInt(user.getId());
        CreateKeyDAO dao = new CreateKeyDAO();
        boolean keyExists = dao.checkKey(uId);
        session.setAttribute("keyExists", keyExists);

        if (user != null) {
            switch (action) {
                case "revokeKey":
                    if (keyExists) {
                        dao.removeAuthKey(uId);
                        response.getWriter().write("Yêu cầu hủy key của bạn đã được xử lý");
                    } else {
                        response.getWriter().write("Yêu cầu hủy key của bạn không thành công");
                    }
                    break;

                case "reportLostKey":
                    if (keyExists) {
                        dao.reportLostKey(uId);
                        response.getWriter().write("Key của bạn đã được đánh dấu là bị lộ. "
                                + "Vui lòng tạo key mới để tiếp tục sử dụng.");
                    } else {
                        response.getWriter().write("Không tìm thấy key đang hoạt động để báo mất");
                    }
                    break;

                case "generateNewKey":
                    if(!keyExists && publicKey != null && !publicKey.isEmpty()){
                        dao.create_key(publicKey,uId);
                        response.getWriter().write("Yêu cầu tạo key mới của bạn đã được xử lý");
                    } else {
                        response.getWriter().write("Yêu cầu tạo key mới của bạn không thành công");
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
