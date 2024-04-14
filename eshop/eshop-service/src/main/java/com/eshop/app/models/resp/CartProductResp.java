package com.eshop.app.models.resp;

import com.eshop.app.common.entities.rdbms.CartProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = CartProductResp.CartProductRespBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartProductResp extends HttpResponse {
    List<CartProduct> cartProducts;
    @JsonPOJOBuilder(withPrefix = "")
    public static class CartProductRespBuilder {}
}
