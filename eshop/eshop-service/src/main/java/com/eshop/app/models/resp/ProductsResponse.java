package com.eshop.app.models.resp;

import com.eshop.app.common.constants.HttpResponseStatus;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.models.req.ProductReqDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = ProductsResponse.ProductsDetailResponseBuilder.class)
public class ProductsResponse extends HttpResponse {
    private static final long serialVersionUID = -4966133033063306971L;
    private List<ProductResp> response;
    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductsDetailResponseBuilder {}
}
