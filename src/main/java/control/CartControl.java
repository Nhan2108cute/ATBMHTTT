package control;

import dao.CartDB_DAO;
import entity.Cart;
import entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CartControl", urlPatterns = {"/cart", "/cart-update", "/cart-remove"})
public class CartControl extends HttpServlet {
    private final long serialVersionUID = 1L;
    private final CartDB_DAO cartDAO = new CartDB_DAO();

    public CartControl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            switch (action) {
                case "/cart":
                    addToCart(request, response, user);
                    break;
                case "/cart-remove":
                    removeFromCart(request, response, user);
                    break;
                default:
                    showCart(request, response, user);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if ("/cart-update".equals(action)) {
            updateCart(request, response, user);
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String productId = request.getParameter("id");
        try {
            Cart cartItem = new Cart();
            cartItem.setUserId(Integer.parseInt(user.getId()));
            cartItem.setProductId(Integer.parseInt(productId));
            cartItem.setQuantity(1);

            boolean success = cartDAO.addToCart(cartItem);
            if (!success) {
                request.setAttribute("error", "Không thể thêm sản phẩm vào giỏ hàng");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi thêm vào giỏ hàng");
        }
        response.sendRedirect("cart.jsp");
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String productId = request.getParameter("id");
        try {
            cartDAO.removeFromCart(Integer.parseInt(user.getId()), Integer.parseInt(productId));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể xóa sản phẩm khỏi giỏ hàng");
        }
        response.sendRedirect("cart.jsp");
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");

        try {
            Cart cartItem = new Cart();
            cartItem.setUserId(Integer.parseInt(user.getId()));
            cartItem.setProductId(Integer.parseInt(productId));
            cartItem.setQuantity(Integer.parseInt(quantity));

            boolean success = cartDAO.updateQuantity(cartItem);
            if (!success) {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"message\": \"Cập nhật thất bại\"}");
                return;
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi hệ thống\"}");
        }
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        request.setAttribute("cartItems", cartDAO.getCartByUserId(Integer.parseInt(user.getId())));
        request.setAttribute("cartTotal", cartDAO.getCartTotal(Integer.parseInt(user.getId())));
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}