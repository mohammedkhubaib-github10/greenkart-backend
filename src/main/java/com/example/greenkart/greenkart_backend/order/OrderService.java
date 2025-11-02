package com.example.greenkart.greenkart_backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    @Transactional
    public Order placeOrder(String cartId, PlaceOrderRequest request) {
        var snapshotOpt = repo.findCartSnapshot(cartId);
        if (snapshotOpt.isEmpty()) {
            throw new RuntimeException("Cart Not Found Exception");
        }
        var snap = snapshotOpt.get();
        if (snap.amount == 0.0) {
            throw new RuntimeException("Empty Cart");
        }

        // build order
        Order order = new Order();
        order.setTotalPrice(snap.amount);
        order.setCustomerId(snap.customerId);
        order.setCartId(cartId);
        order.setDeliveryAddressId(request.getDeliveryAddressId());
        order.setPaymentMode(request.getPaymentMode());
        order.setCreatedAt(LocalDateTime.now());

        // simple payment logic: if paymentMode == "COD" -> Placed else Failed (simple)
        boolean isCod = "COD".equalsIgnoreCase(request.getPaymentMode());
        order.setOrderStatus(isCod ? "Placed" : "Failed");

        // insert order row
        String orderId = repo.insertOrder(order);

        // if order placed, copy cart items -> order items and clear cart
        if ("Placed".equalsIgnoreCase(order.getOrderStatus())) {
            List<OrderItem> cartItems = repo.findCartItems(cartId);
            for (OrderItem ci : cartItems) {
                repo.insertOrderItem(orderId, ci);
            }
            repo.deleteCartItems(cartId);
            repo.updateCartAmountToZero(cartId);
        }

        // load saved order to return (simple)
        List<Order> orders = repo.findOrdersByCustomer(order.getCustomerId());
        return orders.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst().orElse(order);
    }

    public List<Order> getCustomerOrders(String customerId) {
        return repo.findOrdersByCustomer(customerId);
    }

    public List<OrderItem> getOrderItems(String orderId) {
        return repo.findOrderItems(orderId);
    }

    public void deleteOrder(String orderId) {
        repo.deleteOrder(orderId);
    }

    public List<OrderRepository.VendorOrderRow> getVendorOrders(String vendorId) {
        return repo.findVendorOrders(vendorId);
    }

    // DTO for request
    public static class PlaceOrderRequest {
        private String paymentMode;
        private String deliveryAddressId;

        public String getPaymentMode() { return paymentMode; }
        public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

        public String getDeliveryAddressId() { return deliveryAddressId; }
        public void setDeliveryAddressId(String deliveryAddressId) { this.deliveryAddressId = deliveryAddressId; }
    }
}
