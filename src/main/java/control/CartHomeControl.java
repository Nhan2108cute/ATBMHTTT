package control;

import dao.CartDB_DAO;
import entity.User;
import entity.Cart;
import entity.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CartHomeControl", value = "/cart-home")
public class CartHomeControl extends HttpServlet {
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
                Product product = cartDAO.getProductById(productId);

                if (product != null) {
                    // Tính giá dựa trên giá gốc hoặc giá khuyến mãi
                    double finalPrice = product.getPriceDis() > 0 ? product.getPriceDis() : product.getPrice();

                    Cart cartItem = new Cart();
                    cartItem.setUserId(Integer.parseInt(user.getId()));
                    cartItem.setProductId(Integer.parseInt(product.getId()));
                    cartItem.setQuantity(1);
                    cartItem.setPrice(finalPrice);

                    boolean success = cartDAO.addToCart(cartItem);

                    if (success) {
                        session.setAttribute("cart", cartDAO.getCartByUserId(Integer.parseInt(user.getId())));
                    }
                }

                request.getRequestDispatcher("home").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            try {
                switch (action) {
                    case "updateQuantity":
                        int productId = Integer.parseInt(request.getParameter("productId"));
                        int quantity = Integer.parseInt(request.getParameter("quantity"));

                        Cart cartItem = new Cart();
                        cartItem.setUserId(Integer.parseInt(user.getId()));
                        cartItem.setProductId(productId);
                        cartItem.setQuantity(quantity);

                        cartDAO.updateQuantity(cartItem);
                        break;

                    case "removeItem":
                        productId = Integer.parseInt(request.getParameter("productId"));
                        cartDAO.removeFromCart(Integer.parseInt(user.getId()), productId);
                        break;

                    case "clearCart":
                        cartDAO.clearCart(Integer.parseInt(user.getId()));
                        break;
                }

                // Cập nhật lại giỏ hàng trong session
                session.setAttribute("cart", cartDAO.getCartByUserId(Integer.parseInt(user.getId())));
                response.sendRedirect("cart.jsp");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp?error=invalid_data");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
