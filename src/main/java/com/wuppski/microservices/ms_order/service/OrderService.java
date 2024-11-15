package com.wuppski.microservices.ms_order.service;

import com.wuppski.microservices.ms_order.client.InventoryClient;
import com.wuppski.microservices.ms_order.dto.OrderRequest;
import com.wuppski.microservices.ms_order.model.Order;
import com.wuppski.microservices.ms_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    /**
     * Map the Order Request to an Order and save to the DB
     * @param orderRequest incoming order
     */
    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);
        }
        else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in Stock");
        }
        log.info("Order saved successfully");
    }
}
