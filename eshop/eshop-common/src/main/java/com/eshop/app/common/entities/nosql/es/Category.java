package com.eshop.app.common.entities.nosql.es;

import com.eshop.app.common.constants.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Version;

@Builder
@Data
class Category {
    @Field(type = FieldType.Keyword)
    private String categoryID;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Boolean)
    private Status status;

    @Version
    private Long version;
}
