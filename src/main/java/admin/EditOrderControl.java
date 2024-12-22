package admin;

import dao.BillDAO;
import entity.Bill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "edit-order", value = "/edit-order")
public class EditOrderControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy ID từ tham số request
        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new NumberFormatException("ID không hợp lệ hoặc rỗng");
            }
            int id = Integer.parseInt(idParam);

            BillDAO billDAO = new BillDAO();
            Bill bill = billDAO.getBillById(id);

            if (bill != null) {
                request.setAttribute("bill", bill);
                request.getRequestDispatcher("admin/EditOrder.jsp").forward(request, response);
            } else {
                response.sendRedirect("order-control");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("order-control"); // Xử lý khi ID không hợp lệ
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra.");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String newStatus = request.getParameter("status");

        BillDAO billDAO = new BillDAO();

        // Kiểm tra trạng thái hiện tại của đơn hàng
        String currentStatus = billDAO.getBillStatusById(id);
        if ("Huy".equals(currentStatus)) {
            System.out.println("Không thể thay đổi trạng thái đơn hàng ID: " + id + " vì trạng thái hiện tại là Hủy.");
            response.sendRedirect("order-control?error=khong_duoc_thay_doi_trang_thai_huy");
            return;
        }

        // Thay đổi trạng thái nếu hợp lệ
        billDAO.updateBillStatus(id, newStatus);
        response.sendRedirect("order-control");
    }
}
