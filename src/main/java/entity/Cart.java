package entity;

import java.io.Serializable;
import java.util.Date;

public class Cart implements Serializable {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private double price;
    private Date createdAt;
    private Product product;

    // Default constructor
    public Cart() {
    }

    // Constructor with all fields
    public Cart(int id, int userId, int productId, int quantity, double price, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
    }

    // Constructor with product
    public Cart(int id, int userId, Product product, int quantity, double price, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.productId = Integer.parseInt(product.getId());
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.productId = Integer.parseInt(product.getId());
        }
    }

    // Calculate total price for this cart item
    public double getTotalPrice() {
        if (product != null) {
            // Use discounted price if available, otherwise use original price
            double unitPrice = product.getPriceDis() > 0 ? product.getPriceDis() : product.getPrice();
            return unitPrice * quantity;
        }
        return price * quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", product=" + (product != null ? product.getName() : "null") +
                '}';
    }
}