package com.canwia.productapi.dto.converter;

import com.canwia.productapi.dto.ProductDto;
import com.canwia.productapi.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoConverter {

    public ProductDto convertFrom(Product product){
        return new ProductDto(
                product.getId(),
                product.getProductName(),
                product.getInformation(),
                product.getPrice(),
                product.getCategory(),
                product.getImgURL()
        );
    }

    public List<ProductDto> convertFrom(List<Product> productList){
        return productList.stream().map(this::convertFrom).collect(Collectors.toList());
    }
}
