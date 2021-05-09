package com.example.catalogservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;
    private LocalDateTime createAt;
}
