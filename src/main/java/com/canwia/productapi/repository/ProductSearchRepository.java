package com.canwia.productapi.repository;

import com.canwia.productapi.model.ProductSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch, UUID> {
}
