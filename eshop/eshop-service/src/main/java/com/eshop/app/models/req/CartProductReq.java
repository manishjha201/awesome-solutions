package com.eshop.app.models.req;

import com.eshop.app.common.entities.rdbms.CartProduct;
import com.eshop.app.utils.ValidateInputRequestHelper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = CartProductReq.CartProductReqBuilder.class)
public class CartProductReq extends HttpRequest {
    private static final long serialVersionUID = 8657615566592456428L;
    private CartProduct cartItem;

    @Override
    public boolean validate() {
        return !ObjectUtils.isEmpty(cartItem) && ValidateInputRequestHelper.isValidCartProductReq(cartItem);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CartProductReqBuilder {
    }
}
