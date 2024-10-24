package com.canwia.productapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequestCreate {

    private String productName;

    private String information;

    private Double price;

    private String category;

    private String imgURL;

}
