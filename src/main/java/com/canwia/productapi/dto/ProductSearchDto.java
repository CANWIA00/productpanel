package com.canwia.productapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductSearchDto {

    private UUID id;

    private String productName;

    private String information;

    private Double price;

    private String category;

    private String imgURL;

    public ProductSearchDto(UUID id, String productName, String information, Double price, String category, String imgURL) {
        this.id = id;
        this.productName = productName;
        this.information = information;
        this.price = price;
        this.category = category;
        this.imgURL = imgURL;
    }
}
