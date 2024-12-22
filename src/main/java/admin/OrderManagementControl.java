package admin;

import dao.BillDAO;
import dao.DAO;
import entity.Bill;
import entity.Category;
import entity.Product;
import entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "order-control", value = "/order-control")
public class OrderManagementControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if(u == null) {
            response.sendRedirect("login");
        } else {
            if (u.getAdmin() == 1) {
                response.sendRedirect("home");
            } else {
                BillDAO billDAO = new BillDAO();
                List<Bill> listBills = billDAO.getAllBills();

                //update lại status cho mỗi bill
                for (Bill bill : listBills) {
                    billDAO.updateOrderVerificationStatus(bill.getId());
                }

                // refresh lại list bill
                listBills = billDAO.getAllBills();

                request.setAttribute("listBills", listBills);
                request.getRequestDispatcher("admin/OrderManagement.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
