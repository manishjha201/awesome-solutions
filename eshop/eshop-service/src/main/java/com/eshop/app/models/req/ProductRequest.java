package com.eshop.app.models.req;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

@Data
@Builder
@JsonDeserialize(builder = ProductRequest.GetProductsRequestDtoBuilder.class)
public class ProductRequest extends HttpRequest {
    Long productId;

    @Override
    public boolean validate() {
        return ObjectUtils.isNotEmpty(productId);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetProductsRequestDtoBuilder {}
}
