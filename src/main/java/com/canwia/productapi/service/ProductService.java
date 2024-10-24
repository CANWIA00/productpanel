package com.canwia.productapi.service;

import com.canwia.productapi.dto.ProductDto;
import com.canwia.productapi.dto.converter.ProductDtoConverter;
import com.canwia.productapi.dto.request.ProductRequestCreate;
import com.canwia.productapi.exception.CustomStringException;
import com.canwia.productapi.model.Product;
import com.canwia.productapi.model.ProductSearch;
import com.canwia.productapi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductDtoConverter productDtoConverter;
    private final ProductSearchService productSearchService;

    public ProductService(ProductRepository productRepository, ProductDtoConverter productDtoConverter, ProductSearchService productSearchService) {
        this.productRepository = productRepository;
        this.productDtoConverter = productDtoConverter;
        this.productSearchService = productSearchService;
    }

    public ProductDto getProductById(UUID id) {
        //**** Log Info *****/
        logger.info(String.valueOf(id));

        Product product = productRepository.findById(id).orElseThrow(()->new CustomStringException("Custom Exception:findProductById(service)"));
        //**** Log Info *****/
        logger.info(String.valueOf(product));

        return productDtoConverter.convertFrom(product);
    }

    public List<ProductDto> findProductAll() {

        List<Product> productList = productRepository.findAll();
        return productDtoConverter.convertFrom(productList);
    }

    public ProductDto createProduct(ProductRequestCreate productRequestCreate) {

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setProductName(productRequestCreate.getProductName());
        product.setInformation(productRequestCreate.getInformation());
        product.setPrice(productRequestCreate.getPrice());
        product.setCategory(productRequestCreate.getCategory());
        product.setImgURL(productRequestCreate.getImgURL());


        try {
            Product productMongo = productRepository.save(product);
            Product productES = productSearchService.productSearchCreate(product);
            if (!productMongo.getId().equals(productES.getId())){
                 throw new CustomStringException("Custom Exception: [Product Creation Process] Ids don't match when creating product!/Special Error:301");
            }
        }catch (Exception e){
            throw new CustomStringException("Custom Exception: [Product Creation Process] Couldn't create a product in Elasticsearch!/Special Error:302");
        }

        return productDtoConverter.convertFrom(product);

    }


    public ProductDto updateProductById(UUID id, ProductRequestCreate productRequestCreate) {

            Product product = findProductById(id);
            ProductSearch productSearch = productSearchService.findProductSearchById(id);
            if (productSearch.getId().equals(product.getId())){
                productSearchService.productSearchUpdateById(id,productRequestCreate);
               product.setProductName(productRequestCreate.getProductName());
               product.setInformation(productRequestCreate.getInformation());
               product.setPrice(productRequestCreate.getPrice());
               product.setCategory(productRequestCreate.getCategory());
               product.setImgURL(productRequestCreate.getImgURL());
                //**** Log Info *****/
               logger.info(String.valueOf(product));
                return productDtoConverter.convertFrom(productRepository.save(product));
            }else {
                throw new CustomStringException("Custom Exception: [Product Creation Process] Couldn't create a product in Elasticsearch!/Special Error:302");
            }
    }


    public void deleteProductById(UUID id) {
        logger.info(String.valueOf(id));
        logger.info(String.valueOf(findProductById(id)));
        productRepository.deleteById(id);

        productSearchService.productSearchDeleteById(id);

    }

    protected Product findProductById(UUID id){
        return productRepository.findById(id).orElseThrow(()->new CustomStringException("Custom Exception:Class:ProductService:findProductById(UUID id) // SERVICE LAYER ERROR:304"));
    }



}
