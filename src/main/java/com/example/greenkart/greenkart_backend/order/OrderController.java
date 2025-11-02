package com.example.greenkart.greenkart_backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    // POST /order/place_order/{cart_id}
    @PostMapping("/place_order/{cart_id}")
    public ResponseEntity<?> placeOrder(@PathVariable("cart_id") String cartId,
                                        @RequestBody OrderService.PlaceOrderRequest request) {
        try {
            Order order = service.placeOrder(cartId, request);
            return ResponseEntity.ok(order);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Order placement failed");
        }
    }

    // DELETE /order/delete_order/{order_id}
    @DeleteMapping("/delete_order/{order_id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("order_id") String orderId) {
        service.deleteOrder(orderId);
        return ResponseEntity.ok("Successful");
    }

    // GET /order/get_customer_orders/{customer_id}
    @GetMapping("/get_customer_orders/{customer_id}")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable("customer_id") String customerId) {
        List<Order> orders = service.getCustomerOrders(customerId);
        if (orders.isEmpty()) return ResponseEntity.status(404).build();
        return ResponseEntity.ok(orders);
    }

    // GET /order/get_vendor_orders/{vendor_id}
    @GetMapping("/get_vendor_orders/{vendor_id}")
    public ResponseEntity<List<OrderRepository.VendorOrderRow>> getVendorOrders(@PathVariable("vendor_id") String vendorId) {
        List<OrderRepository.VendorOrderRow> rows = service.getVendorOrders(vendorId);
        if (rows.isEmpty()) return ResponseEntity.status(404).build();
        return ResponseEntity.ok(rows);
    }
}
