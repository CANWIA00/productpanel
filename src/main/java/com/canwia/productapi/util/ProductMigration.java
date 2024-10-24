package com.canwia.productapi.util;

import com.canwia.productapi.model.Product;
import com.canwia.productapi.repository.ProductRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductMigration implements DisposableBean {

    private final MongoTemplate mongoTemplate;
    private final ProductRepository productRepository;

    public ProductMigration(MongoTemplate mongoTemplate, ProductRepository productRepository) {
        this.mongoTemplate = mongoTemplate;
        this.productRepository = productRepository;
    }

    @Override
    public void destroy() throws Exception {

        mongoTemplate.getDb().drop();
        System.out.println("All data has been dropped from the database.");
    }

    @Bean
    public CommandLineRunner initialDB(){
        return args -> {
            if (productRepository.count() == 0){
                Product product1 = new Product(
                        UUID.randomUUID(),
                        "Product 1",
                        "This is the first product.",
                        100.0,
                        "Category 1",
                        "http://example.com/image1.jpg"
                );

                Product product2 = new Product(
                        UUID.randomUUID(),
                        "Product 2",
                        "This is the second product.",
                        150.0,
                        "Category 2",
                        "http://example.com/image2.jpg"
                );

                Product product3 = new Product(
                        UUID.randomUUID(),
                        "Product 3",
                        "This is the third product.",
                        200.0,
                        "Category 1",
                        "http://example.com/image3.jpg"
                );

                List<Product> list = Arrays.asList(product1,product2,product3);

                list.stream().map(productRepository::save).collect(Collectors.toList());

                System.out.println("Mongo DB data initialized!");
            }else {
                System.out.println("Product data already initialized, skipping initialization.");
            }
        };
    }


}
