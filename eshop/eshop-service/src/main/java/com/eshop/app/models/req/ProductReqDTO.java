package com.eshop.app.models.req;

import com.eshop.app.common.constants.Currency;
import com.eshop.app.common.constants.Status;
import com.eshop.app.utils.ValidateInputRequestHelper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@JsonDeserialize(builder = ProductReqDTO.ProductReqDTOBuilder.class)
public class ProductReqDTO extends HttpRequest {
    private static final long serialVersionUID = -9170093570486603251L;
    @NotBlank(message = "Product Title is a mandatory field. It should not be null/empty")
    @Size(min = 3, max = 128)
    private String title;
    @NotBlank(message = "Product name is a mandatory field. It should not be null/empty")
    @Size(min = 3, max = 128)
    private String name;
    @NotBlank(message = "Product description is a mandatory field. It should not be null/empty")
    @Size(min = 3, max = 256)
    private String description;
    private BigDecimal price;
    private Currency currency;
    private Boolean inStock;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer minStockQuantity;
    private Status status;
    private Long categoryId;
    @NotBlank(message = "Image url is a mandatory field. It should not be null/empty")
    @Size(min = 3, max = 256)
    private String imageUrl;
    private Long tenantId;

    @Override
    public boolean validate() {
        return ValidateInputRequestHelper.validateProductDTOReq(this);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductReqDTOBuilder {}
}
