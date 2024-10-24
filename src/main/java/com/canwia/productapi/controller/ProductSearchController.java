package com.canwia.productapi.controller;

import com.canwia.productapi.dto.ProductDto;
import com.canwia.productapi.dto.ProductSearchDto;
import com.canwia.productapi.dto.request.ProductSearchRequest;
import com.canwia.productapi.model.ProductSearch;
import com.canwia.productapi.service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product/search")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping//(auto-fill search)
    public ResponseEntity<List<ProductSearchDto>> searchProduct(@RequestBody ProductSearchRequest productSearchRequest){
        return ResponseEntity.ok(productSearchService.searchProduct(productSearchRequest));
    }

    @GetMapping("/test")
    //Test
    public Iterable<ProductSearch> getAllProductTEST(){
        return productSearchService.getAllProductTEST();
    }
}
