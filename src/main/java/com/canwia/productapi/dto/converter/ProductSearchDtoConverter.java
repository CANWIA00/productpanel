package com.canwia.productapi.dto.converter;

import com.canwia.productapi.dto.ProductDto;
import com.canwia.productapi.dto.ProductSearchDto;
import com.canwia.productapi.model.Product;
import com.canwia.productapi.model.ProductSearch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductSearchDtoConverter {

    public ProductSearchDto convertFrom(ProductSearch product){
        return new ProductSearchDto(
                product.getId(),
                product.getProductName(),
                product.getInformation(),
                product.getPrice(),
                product.getCategory(),
                product.getImgURL()
        );
    }

    public List<ProductSearchDto> convertFrom(List<ProductSearch> productList){
        return productList.stream().map(this::convertFrom).collect(Collectors.toList());
    }
}
