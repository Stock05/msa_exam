package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto ){
        return productService.createProduct(requestDto);
    }

    @GetMapping
    public List<ProductResponseDto> findAllProducts(){
        return productService.findAllProducts();
    }

    @GetMapping
    public ProductResponseDto findProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }



}
