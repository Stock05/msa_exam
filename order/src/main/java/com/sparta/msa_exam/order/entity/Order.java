package com.sparta.msa_exam.order.entity;


import com.sparta.msa_exam.order.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "myOrder") //
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_id")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems ;

    public Order(OrderRequestDto requestDto) {
        this.orderItems = requestDto.getProduct_ids().stream().map(productId-> new OrderItem(productId,this)).toList();
    }

    
}
