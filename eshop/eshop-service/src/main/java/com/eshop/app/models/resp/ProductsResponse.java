package com.eshop.app.models.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = ProductsResponse.ProductsDetailResponseBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsResponse extends HttpResponse {
    private static final long serialVersionUID = -4966133033063306971L;
    private ProductResp response;
    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductsDetailResponseBuilder {}
}
