package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;

import com.sparta.msa_exam.order.entity.OrderItem;
import com.sparta.msa_exam.order.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductClient productClient;


    @CircuitBreaker(name = "checkProductStock", fallbackMethod = "fallbackCheckProductStock")
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        try{
            requestDto.getProduct_ids().forEach(productClient::getProduct);
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new RuntimeException(e);
        }
        Order order = new Order(requestDto);
        Order saveOrder = orderRepository.save(order);
        return new OrderResponseDto(saveOrder);
    }
    public OrderResponseDto fallbackCheckProductStock(OrderRequestDto requestDto, Throwable t) {
        log.error("checkProductStock {}", t.getMessage());
        return new OrderResponseDto("잠시 후에 주문 추가를 요청 해주세요");
    }

    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Product not found with id " + orderId));
        requestDto.getProduct_ids().forEach(productId -> {
            try{productClient.getProduct(productId);}
            catch (Exception e) {
                log.error("Exception: ", e);
                throw new RuntimeException(e);

            }

                    OrderItem orderItem = new OrderItem(productId, order);
                    order.getOrderItems().add(orderItem);
        });
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    public OrderResponseDto findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found with id " + orderId));
        return new OrderResponseDto(order);
    }
}
