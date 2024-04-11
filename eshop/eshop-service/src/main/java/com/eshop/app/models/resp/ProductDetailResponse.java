package com.eshop.app.models.resp;

import com.eshop.app.common.constants.HttpResponseStatus;
import com.eshop.app.common.entities.rdbms.Product;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = ProductDetailResponse.ProductDetailResponseBuilder.class)
public class ProductDetailResponse extends HttpResponse {
    private static final long serialVersionUID = -4966133033063306971L;
    private Product response;
    private HttpResponseStatus lastUpdatedStatus;
    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductDetailResponseBuilder {}
}
