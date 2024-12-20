package control;

import dao.CartDB_DAO;
import entity.Product;
import entity.User;
import entity.Cart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CartControl", value = "/cart")
public class CartControl extends HttpServlet {
    private final CartDB_DAO cartDAO = new CartDB_DAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdParam = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null && productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);

                // Lấy thông tin sản phẩm từ database
                Product product = cartDAO.getProductById(productId);
                if (product == null) {
                    response.sendRedirect("error.jsp?error=product_not_found");
                    return;
                }

                // Tính giá sản phẩm
                double price = product.getPrice();

                // Tạo đối tượng CartItem với đầy đủ thông tin
                Cart cartItem = new Cart();
                cartItem.setUserId(Integer.parseInt(user.getId()));
                cartItem.setProductId(productId);
                cartItem.setQuantity(1); // Mặc định số lượng là 1
                cartItem.setPrice(price); // Set giá sản phẩm

                // Thêm sản phẩm vào giỏ hàng
                boolean success = cartDAO.addToCart(cartItem);

                if (success) {
                    // Fetch updated cart và lưu vào session
                    session.setAttribute("cart", cartDAO.getCartByUserId(Integer.parseInt(user.getId())));
                }

                response.sendRedirect("home");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp?error=invalid_product_id");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
