package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long product_id;
    private String product_name;
    private Integer supply_price;
    private String message;


    public ProductResponseDto(Product product) {
        this.product_id = product.getId();
        this.product_name = product.getName();
        this.supply_price = product.getSupply_price();
    }

    public ProductResponseDto(String message) {
        this.message = message;
    }
}
