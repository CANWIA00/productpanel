package com.canwia.productapi.service;

import com.canwia.productapi.ProductTestSupport;
import com.canwia.productapi.dto.ProductDto;
import com.canwia.productapi.dto.converter.ProductDtoConverter;
import com.canwia.productapi.dto.request.ProductRequestCreate;
import com.canwia.productapi.model.Product;
import com.canwia.productapi.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;

class ProductServiceTest extends ProductTestSupport {


    private  ProductRepository productRepository;
    private ProductDtoConverter productDtoConverter;

    private ProductSearchService productSearchService;

    private ProductService productService;

    @BeforeEach
    public void setup() {
        productRepository = mock(ProductRepository.class);
        productDtoConverter = mock(ProductDtoConverter.class);
        productSearchService = mock(ProductSearchService.class);

         productService = new ProductService(productRepository, productDtoConverter, productSearchService);
    }


    @Test
    void getProductById_whenValidUUID_thenReturnValidDTO() {

        UUID id = UUID.randomUUID();
        Product product =generateProduct(id);
        ProductDto productDto = generateProductDto(id);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(productDtoConverter.convertFrom(product)).thenReturn(productDto);


        ProductDto result = productService.getProductById(id);
        Assertions.assertEquals(result, productDto);


        verify(productRepository).findById(product.getId());
        verify(productDtoConverter).convertFrom(product);

        Mockito.reset(productDtoConverter);
        Mockito.reset(productRepository);
    }

    @Test
    void findProductAll() {

        UUID id = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Product product = generateProduct(id);
        Product product1 = generateProduct(id1);
        Product product2 = generateProduct(id2);
        List<Product> productList = new ArrayList<>(Arrays.asList(product, product1, product2));

        ProductDto productDto = generateProductDto(id);
        ProductDto productDto1 = generateProductDto(id1);
        ProductDto productDto2 = generateProductDto(id2);
        List<ProductDto> productListDto = new ArrayList<>(Arrays.asList(productDto, productDto1, productDto2));


        when(productRepository.findAll()).thenReturn(productList);
        when(productDtoConverter.convertFrom(productList)).thenReturn(productListDto);

        List<ProductDto> result = productService.findProductAll();

        Assertions.assertEquals(result,productListDto);

        verify(productRepository).findAll();
        verify(productDtoConverter).convertFrom(productList);

        Mockito.reset(productDtoConverter);
        Mockito.reset(productRepository);
    }

    @Test
    void createProduct() {
        UUID id = UUID.randomUUID();
        Product product = generateProduct(id);
        ProductDto productDto = generateProductDto(id);

        ProductRequestCreate productRequestCreate = generateProductSearchRequest();


        when(productRepository.save(product)).thenReturn(product);
        when(productSearchService.productSearchCreate(product)).thenReturn(product);
        when(productDtoConverter.convertFrom(product)).thenReturn(productDto);

        ProductDto result = productService.createProduct(productRequestCreate);

        Assertions.assertEquals(result.getProductName(),productDto.getProductName());

        verify(productRepository).save(product);
        verify(productSearchService).productSearchCreate(product);
        verify(productDtoConverter).convertFrom(product);

        Mockito.reset(productDtoConverter);
        Mockito.reset(productRepository);
        Mockito.reset(productSearchService);

    }

    @Test
    void updateProductById() {
    }

    @Test
    void deleteProductById() {
    }

    @Test
    void findProductById() {
    }
}