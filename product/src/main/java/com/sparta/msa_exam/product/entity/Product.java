package com.sparta.msa_exam.product.entity;


import com.sparta.msa_exam.product.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_id")
    private Long id;
    private String name;
    private Integer supply_price;

    public Product(ProductRequestDto  requestDto) {
        this.name = requestDto.getProduct_name();
        this.supply_price = requestDto.getSupply_price();
    }



}
