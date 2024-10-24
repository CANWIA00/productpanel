package com.canwia.productapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;


@Document(indexName = "productsearch")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSearch {

    @Id
    private UUID id;

    private String productName;

    private String information;

    private Double price;

    private String category;

    private String imgURL;
}
