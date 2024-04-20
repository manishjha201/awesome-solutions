package com.eshop.app.common.entities.nosql.es;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Data
public class Detail {
    @Field(type = FieldType.Keyword)
    private String key;

    @Field(type = FieldType.Text)
    private String value;
}
