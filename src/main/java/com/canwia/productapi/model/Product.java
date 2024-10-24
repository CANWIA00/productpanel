package com.canwia.productapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {


    @Id
    private UUID id;

    private String productName;

    private String information;

    private Double price;

    private String category;

    private String imgURL;

}
