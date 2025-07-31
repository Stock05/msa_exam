package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto ){
        return orderService.createOrder(requestDto);
    }

    @PutMapping("/{orderId}")
    public OrderResponseDto updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDto requestDto){
        return orderService.updateOrder(orderId,requestDto);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto findOrderById(@PathVariable Long orderId){
        return orderService.findOrderById(orderId);
    }








}
