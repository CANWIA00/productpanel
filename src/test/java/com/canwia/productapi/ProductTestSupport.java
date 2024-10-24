package com.canwia.productapi;

import com.canwia.productapi.dto.ProductDto;
import com.canwia.productapi.dto.ProductSearchDto;
import com.canwia.productapi.dto.request.ProductRequestCreate;
import com.canwia.productapi.model.Product;
import com.canwia.productapi.model.ProductSearch;

import java.util.UUID;

public class ProductTestSupport {


    public ProductDto generateProductDto(UUID id){
        return new ProductDto(
                id,
                "Product 1",
                "This is the first product.",
                100.0,
                "Category 1",
                "https://example.com/image1.jpg"
        );
    }


    public Product generateProduct(UUID id){
        return new Product(
                id,
                "Product 1",
                "This is the first product.",
                100.0,
                "Category 1",
                "https://example.com/image1.jpg"
        );
    }

    public ProductSearch generateProductSearch(UUID id){
        return new ProductSearch(
                id,
                "Product 1",
                "This is the first product.",
                100.0,
                "Category 1",
                "https://example.com/image1.jpg"
        );
    }

    public ProductSearchDto generateProductSearchDto(UUID id){
        return new ProductSearchDto(
                id,
                "Product 1",
                "This is the first product.",
                100.0,
                "Category 1",
                "https://example.com/image1.jpg"
        );
    }

    public ProductRequestCreate generateProductSearchRequest(){
        return new ProductRequestCreate(
                "Product 1",
                "This is the first product.",
                100.0,
                "Category 1",
                "https://example.com/image1.jpg"
        );
    }
}
