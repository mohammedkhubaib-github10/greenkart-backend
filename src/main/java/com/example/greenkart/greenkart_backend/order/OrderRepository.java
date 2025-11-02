package com.example.greenkart.greenkart_backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // map order row
    private Order mapOrder(ResultSet rs, int rowNum) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getString("order_id"));
        o.setTotalPrice(rs.getDouble("total_price"));
        o.setOrderStatus(rs.getString("order_status"));
        o.setPaymentMode(rs.getString("payment_mode"));
        o.setDeliveryAddressId(rs.getString("delivery_address_id"));
        o.setCustomerId(rs.getString("customer_id"));
        o.setCartId(rs.getString("cart_id"));
        o.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return o;
    }

    // map order item
    private OrderItem mapOrderItem(ResultSet rs, int rowNum) throws SQLException {
        OrderItem it = new OrderItem();
        it.setItemId(rs.getString("item_id"));
        it.setOrderId(rs.getString("order_id"));
        it.setProductId(rs.getString("product_id"));
        it.setQty(rs.getInt("qty"));
        it.setTotalPrice(rs.getDouble("total_price"));
        return it;
    }

    public Optional<CartSnapshot> findCartSnapshot(String cartId) {
        // fetch cart total and customer id
        String sql = "SELECT cart_id, customer_id, amount FROM Carts WHERE cart_id = ?";
        List<CartSnapshot> list = jdbcTemplate.query(sql, (rs, rn) -> {
            CartSnapshot cs = new CartSnapshot();
            cs.cartId = rs.getString("cart_id");
            cs.customerId = rs.getString("customer_id");
            cs.amount = rs.getDouble("amount");
            return cs;
        }, cartId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<OrderItem> findCartItems(String cartId) {
        String sql = "SELECT * FROM Cart_Items WHERE cart_id = ?";
        return jdbcTemplate.query(sql, this::mapCartItemToOrderItem, cartId);
    }

    private OrderItem mapCartItemToOrderItem(ResultSet rs, int rowNum) throws SQLException {
        OrderItem it = new OrderItem();
        it.setItemId(rs.getString("item_id"));
        // when copying to order, itemId will be replaced but keep fields
        it.setProductId(rs.getString("product_id"));
        it.setQty(rs.getInt("qty"));
        it.setTotalPrice(rs.getDouble("total_price"));
        return it;
    }

    public String insertOrder(Order order) {
        String sql = "INSERT INTO Orders (order_id, total_price, order_status, payment_mode, delivery_address_id, customer_id, cart_id, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String id = UUID.randomUUID().toString();
        jdbcTemplate.update(sql, id, order.getTotalPrice(), order.getOrderStatus(), order.getPaymentMode(),
                order.getDeliveryAddressId(), order.getCustomerId(), order.getCartId(),
                java.sql.Timestamp.valueOf(order.getCreatedAt()));
        return id;
    }

    public void insertOrderItem(String orderId, OrderItem item) {
        String sql = "INSERT INTO Order_Items (item_id, order_id, product_id, qty, total_price) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), orderId, item.getProductId(), item.getQty(), item.getTotalPrice());
    }

    public void deleteCartItems(String cartId) {
        jdbcTemplate.update("DELETE FROM Cart_Items WHERE cart_id = ?", cartId);
    }

    public void updateCartAmountToZero(String cartId) {
        jdbcTemplate.update("UPDATE Carts SET amount = 0 WHERE cart_id = ?", cartId);
    }

    public List<Order> findOrdersByCustomer(String customerId) {
        String sql = "SELECT * FROM Orders WHERE customer_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, this::mapOrder, customerId);
    }

    public List<OrderItem> findOrderItems(String orderId) {
        String sql = "SELECT * FROM Order_Items WHERE order_id = ?";
        return jdbcTemplate.query(sql, this::mapOrderItem, orderId);
    }

    public void deleteOrder(String orderId) {
        jdbcTemplate.update("DELETE FROM Orders WHERE order_id = ?", orderId);
    }

    // Vendor orders: join query
    public List<VendorOrderRow> findVendorOrders(String vendorId) {
        String sql = """
                SELECT o.order_id, o.total_price, o.created_at, c.customer_name, c.contact,
                       ca.street, ca.city, ca.pincode, ca.flat_no,
                       p.product_name, oi.qty, oi.total_price, o.payment_mode
                FROM Orders o
                JOIN Order_Items oi ON o.order_id = oi.order_id
                JOIN Products p ON oi.product_id = p.product_id
                JOIN Customers c ON o.customer_id = c.customer_id
                JOIN Customer_Addresses ca ON o.delivery_address_id = ca.address_id
                WHERE p.vendor_id = ? AND o.order_status = 'Placed'
                ORDER BY o.created_at DESC
                """;
        return jdbcTemplate.query(sql, (rs, rn) -> {
            VendorOrderRow r = new VendorOrderRow();
            r.orderId = rs.getString(1);
            r.totalPrice = rs.getDouble(2);
            r.createdAt = rs.getTimestamp(3).toLocalDateTime();
            r.customerName = rs.getString(4);
            r.customerContact = rs.getString(5);
            r.street = rs.getString(6);
            r.city = rs.getString(7);
            r.pincode = rs.getString(8);
            r.flatNo = rs.getString(9);
            r.productName = rs.getString(10);
            r.qty = rs.getInt(11);
            r.itemTotalPrice = rs.getDouble(12);
            r.paymentMode = rs.getString(13);
            return r;
        }, vendorId);
    }

    // simple helpers / internal DTOs
    public static class CartSnapshot {
        public String cartId;
        public String customerId;
        public double amount;
    }

    public static class VendorOrderRow {
        public String orderId;
        public double totalPrice;
        public java.time.LocalDateTime createdAt;
        public String customerName;
        public String customerContact;
        public String street;
        public String city;
        public String pincode;
        public String flatNo;
        public String productName;
        public int qty;
        public double itemTotalPrice;
        public String paymentMode;
    }
}
