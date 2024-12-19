package dao;

import context.DBConnect;
import entity.Cart;
import entity.CartItem;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDB_DAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public Cart getCartByUser(String userId) {
        Cart cart = null;
        String query = "SELECT * FROM cart WHERE user_id = ? ORDER BY created_date DESC LIMIT 1";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getLong("cart_id"));
                cart.setUserId(userId);
                cart.setCreatedDate(rs.getTimestamp("created_date"));
                cart.setItems(getCartItems(cart.getCartId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    public List<CartItem> getCartItems(long cartId) {
        List<CartItem> items = new ArrayList<>();
        String query = "SELECT ci.*, p.* FROM cart_item ci " +
                "JOIN sanpham p ON ci.product_id = p.id " +
                "WHERE ci.cart_id = ?";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, cartId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getLong("id"));
                item.setCartId(cartId);
                item.setProductId(rs.getString("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));

                Product product = new Product();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDes(rs.getString("des"));
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setPriceDis(rs.getDouble("priceDis"));
                product.setCateID(rs.getString("cateID"));

                item.setProduct(product);
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean addToCart(String userId, String productId) {
        try {
            conn = new DBConnect().getConnection();

            // Get or create cart
            Cart cart = getCartByUser(userId);
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);

                String insertCart = "INSERT INTO cart (cart_id, user_id) VALUES (?, ?)";
                ps = conn.prepareStatement(insertCart);
                ps.setLong(1, cart.getCartId());
                ps.setString(2, userId);
                ps.executeUpdate();
            }

            // Check if product already exists in cart
            String checkQuery = "SELECT * FROM cart_item WHERE cart_id = ? AND product_id = ?";
            ps = conn.prepareStatement(checkQuery);
            ps.setLong(1, cart.getCartId());
            ps.setString(2, productId);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Update quantity
                String updateQuery = "UPDATE cart_item SET quantity = quantity + 1 WHERE cart_id = ? AND product_id = ?";
                ps = conn.prepareStatement(updateQuery);
                ps.setLong(1, cart.getCartId());
                ps.setString(2, productId);
                ps.executeUpdate();
            } else {
                // Add new item
                Product product = new DAO().getProductByID(productId);
                String insertItem = "INSERT INTO cart_item (cart_id, product_id, price) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(insertItem);
                ps.setLong(1, cart.getCartId());
                ps.setString(2, productId);
                ps.setDouble(3, product.getPrice());
                ps.executeUpdate();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromCart(String userId, String productId) {
        try {
            Cart cart = getCartByUser(userId);
            if (cart != null) {
                String query = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";
                conn = new DBConnect().getConnection();
                ps = conn.prepareStatement(query);
                ps.setLong(1, cart.getCartId());
                ps.setString(2, productId);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clearCart(String userId) {
        try {
            Cart cart = getCartByUser(userId);
            if (cart != null) {
                String query = "DELETE FROM cart_item WHERE cart_id = ?";
                conn = new DBConnect().getConnection();
                ps = conn.prepareStatement(query);
                ps.setLong(1, cart.getCartId());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
