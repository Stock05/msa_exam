package com.sparta.msa_exam.order.dto;


import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private List<Long> productIds;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.productIds = order.getOrderItems().stream().map(OrderItem::getProductId).toList();
    }
}
