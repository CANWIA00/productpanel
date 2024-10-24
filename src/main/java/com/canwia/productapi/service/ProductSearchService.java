package com.canwia.productapi.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.canwia.productapi.dto.ProductSearchDto;
import com.canwia.productapi.dto.converter.ProductSearchDtoConverter;
import com.canwia.productapi.dto.request.ProductRequestCreate;
import com.canwia.productapi.dto.request.ProductSearchRequest;
import com.canwia.productapi.exception.CustomStringException;
import com.canwia.productapi.model.Product;
import com.canwia.productapi.model.ProductSearch;
import com.canwia.productapi.repository.ProductSearchRepository;
import com.canwia.productapi.util.EsUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final ProductSearchDtoConverter productSearchDtoConverter;




    public List<ProductSearchDto> searchProduct(ProductSearchRequest productSearchRequest) {

        Supplier<Query> querySupplier = EsUtil.buildFieldForFieldAndValue(productSearchRequest.getFieldName(), productSearchRequest.getSearchValue());

        SearchResponse<ProductSearch> searchResponse = null;

        try{
            searchResponse = elasticsearchClient.search(q->q.index("productsearch").
                    query(querySupplier.get()),ProductSearch.class);

        }catch (IOException e){
            throw  new RuntimeException(e);
        }

        return productSearchDtoConverter.convertFrom(extractProductFromResponse(searchResponse));
    }

    public List<ProductSearch> extractProductFromResponse(SearchResponse<ProductSearch> searchResponse){
        return searchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
    }


    /******       Sub Methods             *******/


    protected Product productSearchCreate(Product product){
        ProductSearch productSearch = new ProductSearch();
        productSearch.setId(product.getId());
        productSearch.setProductName(product.getProductName());
        productSearch.setInformation(product.getInformation());
        productSearch.setPrice(product.getPrice());
        productSearch.setCategory(product.getCategory());
        productSearch.setImgURL(product.getImgURL());

        productSearchRepository.save(productSearch);
        return product;
    }

    protected void productSearchUpdateById(UUID id, ProductRequestCreate productRequestCreate){
        ProductSearch productSearch = findProductSearchById(id);

        productSearch.setProductName(productRequestCreate.getProductName());
        productSearch.setInformation(productRequestCreate.getInformation());
        productSearch.setCategory(productRequestCreate.getCategory());
        productSearch.setPrice(productRequestCreate.getPrice());
        productSearch.setImgURL(productRequestCreate.getImgURL());

        productSearchRepository.save(productSearch);
    }

    protected void productSearchDeleteById(UUID id){
        productSearchRepository.deleteById(id);
    }


    protected ProductSearch findProductSearchById(UUID id) {
        return productSearchRepository.findById(id).orElseThrow(()->
                        new CustomStringException(" Custom Exception:Class:ProductSearchService:findProductSearchById(UUID id) // SERVICE LAYER ERROR:301"));

    }


    public Iterable<ProductSearch> getAllProductTEST() {
        return productSearchRepository.findAll();
    }
}
