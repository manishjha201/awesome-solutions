package com.eshop.app.models.req;

import com.eshop.app.common.constants.PaymentMethod;
import com.eshop.app.common.constants.PaymentStatus;
import com.eshop.app.utils.ValidateInputRequestHelper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = CartPaymentReq.CartPaymentReqBuilder.class)
public class CartPaymentReq extends HttpRequest {
    private Long id;
    private Long userId;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String paymentChannel;
    private boolean isActive;
    private Long tenantId;
    private Integer version;

    @Override
    public boolean validate() {
        return ValidateInputRequestHelper.validatePaymentRequest(this);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CartPaymentReqBuilder {
    }
}
