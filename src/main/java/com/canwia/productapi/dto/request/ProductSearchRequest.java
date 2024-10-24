package com.canwia.productapi.dto.request;

import lombok.Data;

@Data
public class ProductSearchRequest {

    private String fieldName;

    private String searchValue;
}
