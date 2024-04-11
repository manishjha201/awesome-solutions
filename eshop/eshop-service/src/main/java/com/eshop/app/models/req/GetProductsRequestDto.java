package com.eshop.app.models.req;

import com.eshop.app.utils.ValidateInputRequestHelper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = GetProductsRequestDto.GetProductsRequestDtoBuilder.class)
public class GetProductsRequestDto extends HttpRequest {
    List<Long> productIds;

    @Override
    public boolean validate() {
        return !productIds.isEmpty() && ValidateInputRequestHelper.validateReqSize(productIds.size());
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetProductsRequestDtoBuilder {}
}
