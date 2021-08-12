package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {

//    @JsonProperty(value = "order_id")
    private String order_id;

//    @JsonProperty(value = "user_id")
    private String user_id;

//    @JsonProperty(value = "product_id")
    private String product_id;

    private int qty;

//    @JsonProperty(value = "unit_price")
    private int unit_price;

//    @JsonProperty(value = "total_price")
    private int total_price;
}
