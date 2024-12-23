package admin;

import dao.DAO;
import entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "admin-home", value = "/admin-home")
public class AdminHomeControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if(u == null) {
            response.sendRedirect("login");
        } else {
            if(u.getAdmin() == 1) {
                response.sendRedirect("home");
            } else {
                DAO dao = new DAO();
                int totalU = dao.getTotalUser();
                int totalP = dao.getTotalProduct();
                int totalB = dao.getTotalBill();
                //int totalPAOOS = dao.getTotalProductAlmostOutOfStock();
                List<User> listU = dao.getNewUser();

                request.setAttribute("user", u);
                request.setAttribute("totalU", totalU);
                request.setAttribute("totalP", totalP);
                request.setAttribute("totalB", totalB);
                //request.setAttribute("totalPAOOS", totalPAOOS);
                request.setAttribute("listU", listU);
                request.getRequestDispatcher("admin/admin-home.jsp").forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
