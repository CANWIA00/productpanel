package com.canwia.productapi.util;

import com.canwia.productapi.model.ProductSearch;
import com.canwia.productapi.repository.ProductSearchRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class ProductSearchMigration implements DisposableBean {


    private final ProductSearchRepository productSearchRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;


    public ProductSearchMigration(ProductSearchRepository productSearchRepository, ElasticsearchTemplate elasticsearchTemplate) {
        this.productSearchRepository = productSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public void destroy() throws Exception {
        elasticsearchTemplate.indexOps(ProductSearch.class).delete();
        System.out.println("Elasticsearch index dropped.");
    }


    @Bean
    public CommandLineRunner initElasticsearchData() {
        return args -> {

            if (productSearchRepository.count() == 0) {

                ProductSearch product1 = new ProductSearch(
                        UUID.randomUUID(),
                        "Product 1",
                        "This is the first product.",
                        100.0,
                        "Category 1",
                        "http://example.com/image1.jpg"
                );

                ProductSearch product2 = new ProductSearch(
                        UUID.randomUUID(),
                        "Product 2",
                        "This is the second product.",
                        150.0,
                        "Category 2",
                        "http://example.com/image2.jpg"
                );

                ProductSearch product3 = new ProductSearch(
                        UUID.randomUUID(),
                        "Product 3",
                        "This is the third product.",
                        200.0,
                        "Category 1",
                        "http://example.com/image3.jpg"
                );

                productSearchRepository.saveAll(Arrays.asList(product1, product2, product3));
                System.out.println("Elasticsearch data initialized!");
            } else {
                System.out.println("Elasticsearch data already initialized, skipping initialization.");
            }
        };
    }

}
