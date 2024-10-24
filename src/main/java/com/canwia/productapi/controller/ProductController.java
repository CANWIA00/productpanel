package com.canwia.productapi.controller;

import com.canwia.productapi.dto.ProductDto;
import com.canwia.productapi.dto.request.ProductRequestCreate;
import com.canwia.productapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findBy/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProductAll(){
        return ResponseEntity.ok(productService.findProductAll());
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductRequestCreate productRequestCreate){
        return ResponseEntity.ok(productService.createProduct(productRequestCreate));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable UUID id, @RequestBody ProductRequestCreate productRequestCreate){
        return ResponseEntity.ok(productService.updateProductById(id,productRequestCreate));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID id){
        productService.deleteProductById(id);
        return new ResponseEntity<>(
                "Product Successfully Deleted",
                HttpStatus.OK
        );
    }


}
