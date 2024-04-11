package com.eshop.app.models.resp;

import com.eshop.app.common.constants.HttpResponseStatus;
import com.eshop.app.common.entities.rdbms.Product;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = ProductResp.ProductRespBuilder.class)
public class ProductResp {
    private Product response;
    private HttpResponseStatus status;
    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductRespBuilder {}
}
