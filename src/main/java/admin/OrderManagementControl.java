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
        BillDAO billDAO = new BillDAO();
        List<Bill> listBills = billDAO.getAllBills();

        // Thêm log để kiểm tra trạng thái
        for (Bill bill : listBills) {
            System.out.println("ID: " + bill.getId() + ", Status: " + bill.getStatus());
        }

        request.setAttribute("listBills", listBills);
        request.getRequestDispatcher("admin/OrderManagement.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
