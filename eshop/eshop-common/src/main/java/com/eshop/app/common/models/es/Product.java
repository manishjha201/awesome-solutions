package com.eshop.app.common.models.es;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Version;
import java.util.List;


@Builder
@Data
@Document(indexName = "catalog")
public class Product {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String productID;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String currency;

    @Field(type = FieldType.Boolean)
    private Boolean isActive;

    @Version
    private Long version;

    @Field(type = FieldType.Object)
    private Category category;

    @Field(type = FieldType.Object)
    private Inventory inventory;

    @Field(type = FieldType.Object)
    private Tenant tenant;

    @Field(type = FieldType.Keyword)
    private String imageUrl;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @Field(type = FieldType.Nested)
    private List<Detail> details;

}
