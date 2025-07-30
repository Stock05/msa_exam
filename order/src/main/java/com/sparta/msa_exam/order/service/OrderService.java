package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {


    private final OrderRepository orderRepository;

    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = new Order(requestDto);
        Order saveOrder = orderRepository.save(order);
        return new OrderResponseDto(saveOrder);

    }
}
