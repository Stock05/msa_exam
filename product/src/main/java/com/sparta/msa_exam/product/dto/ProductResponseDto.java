package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductResponseDto {
    private final Long product_id;
    private final String product_name;
    private final Integer supply_price;

    public ProductResponseDto(Product product) {
        this.product_id = product.getId();
        this.product_name = product.getName();
        this.supply_price = product.getSupply_price();
    }
}
