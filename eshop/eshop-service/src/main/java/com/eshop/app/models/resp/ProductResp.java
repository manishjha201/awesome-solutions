package com.eshop.app.models.resp;

import com.eshop.app.common.models.kafka.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ProductResp.ProductRespBuilder.class)
public class ProductResp extends HttpResponse {
    private String responseStatus;
    private Product product;

    public ProductResp(ProductResp productResp) {
        this.responseStatus = productResp.responseStatus;
        this.product = productResp.getProduct();
    }

    public ProductResp(ProductRespBuilder productRespBuilder) {
        this.responseStatus = productRespBuilder.responseStatus;
        this.product = productRespBuilder.product;
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class ProductRespBuilder {
        private String responseStatus;
        private Product product;

        @JsonProperty("response")
        private void unpackNestedResponse(Map<String, Object> response) {
            this.responseStatus = (String) response.get("responseStatus");
            Map<String, Object> productMap = (Map<String, Object>) response.get("product");

            ObjectMapper objectMapper = new ObjectMapper();
            this.product = objectMapper.convertValue(productMap, Product.class);
        }

        public ProductResp build() {
            return new ProductResp(this);
        }
    }
}

