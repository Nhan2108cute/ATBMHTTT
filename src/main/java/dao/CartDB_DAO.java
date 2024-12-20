package dao;

import context.DBConnect;
import entity.Cart;
import entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDB_DAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    // Lấy danh sách sản phẩm trong giỏ hàng của người dùng
    public List<Cart> getCartByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String query = "SELECT c.*, p.* FROM cart c " +
                "INNER JOIN sanpham p ON p.id = c.product_id " +
                "WHERE c.user_id = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Tạo đối tượng Product
                Product product = new Product(
                        String.valueOf(rs.getInt("p.id")),
                        rs.getString("tensp"),
                        rs.getString("mota"),
                        rs.getString("hinhanh"),
                        rs.getDouble("giagoc"),
                        rs.getDouble("giakm"),
                        String.valueOf(rs.getInt("id_loaisp"))
                );

                // Tính giá trị sản phẩm sau giảm giá
                double price = product.getPriceDis() > 0 ? product.getPriceDis() : product.getPrice();

                // Tạo đối tượng Cart với sản phẩm
                Cart cartItem = new Cart(
                        rs.getInt("c.id"),
                        rs.getInt("user_id"),
                        product,
                        rs.getInt("quantity"),
                        price,
                        rs.getTimestamp("created_at")
                );

                cartItems.add(cartItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return cartItems;
    }

    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(Cart cart) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBConnect().getConnection();
            conn.setAutoCommit(false);

            // Check existing cart item
            String checkQuery = "SELECT id FROM cart WHERE user_id = ? AND product_id = ?";
            ps = conn.prepareStatement(checkQuery);
            ps.setInt(1, cart.getUserId());
            ps.setInt(2, cart.getProductId());
            rs = ps.executeQuery();

            // Get product first
            Product product = getProductById(cart.getProductId());
            if (product == null) {
                return false;
            }

            // Calculate price
            double finalPrice = product.getPriceDis() > 0 ? product.getPriceDis() : product.getPrice();

            if (rs.next()) {
                String updateQuery = "UPDATE cart SET quantity = quantity + ?, price = ? WHERE user_id = ? AND product_id = ?";
                ps = conn.prepareStatement(updateQuery);
                ps.setInt(1, cart.getQuantity());
                ps.setDouble(2, finalPrice);
                ps.setInt(3, cart.getUserId());
                ps.setInt(4, cart.getProductId());
            } else {
                String insertQuery = "INSERT INTO cart (user_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                ps = conn.prepareStatement(insertQuery);
                ps.setInt(1, cart.getUserId());
                ps.setInt(2, cart.getProductId());
                ps.setInt(3, cart.getQuantity());
                ps.setDouble(4, finalPrice);
            }

            int result = ps.executeUpdate();
            conn.commit();
            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // Sửa để nhận connection là tham số
    private Product getProductById(Connection conn, int productId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = null;

        try {
            String query = "SELECT * FROM sanpham WHERE id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("tensp"),
                        rs.getString("mota"),
                        rs.getString("hinhanh"),
                        rs.getDouble("giagoc"),
                        rs.getDouble("giakm"),
                        String.valueOf(rs.getInt("id_loaisp"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    // Giữ lại phương thức public cho các trường hợp khác
    public Product getProductById(int productId) {
        Connection conn = null;
        Product product = null;
        try {
            conn = new DBConnect().getConnection();
            product = getProductById(conn, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public boolean removeFromCart(int userId, int productId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Xóa tất cả sản phẩm trong giỏ hàng của người dùng
    public boolean clearCart(int userId) {
        String query = "DELETE FROM cart WHERE user_id = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật số lượng và giá sản phẩm trong giỏ hàng
    public boolean updateQuantity(Cart cart) {
        String query = "UPDATE cart SET quantity = ?, price = ? WHERE user_id = ? AND product_id = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, cart.getQuantity());
            ps.setDouble(2, cart.getPrice());
            ps.setInt(3, cart.getUserId());
            ps.setInt(4, cart.getProductId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tính tổng giá trị các sản phẩm trong giỏ hàng
    public double getCartTotal(int userId) {
        double total = 0;
        String query = "SELECT SUM(c.quantity * c.price) as total FROM cart c WHERE c.user_id = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Đóng tài nguyên kết nối
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
