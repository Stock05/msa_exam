package com.sparta.msa_exam.order.dto;


import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor

public class OrderResponseDto {
    private Long orderId;
    private List<Long> productIds;
    private String message;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.productIds = order.getOrderItems().stream().map(OrderItem::getProductId).toList();
    }

    public OrderResponseDto(String message) {
        this.message = message;
    }
}
