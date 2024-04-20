package com.eshop.app.models.req;

import com.eshop.app.common.constants.Status;
import com.eshop.app.utils.ValidateInputRequestHelper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = CatalogSearchQueryDto.CatalogSearchQueryDtoBuilder.class)
public class CatalogSearchQueryDto extends HttpRequest {
    private static final long serialVersionUID = -2538414262542034643L;

    private String searchKey;
    private String searchValue;
    private int pageNumber;
    private int pageSize;
    private List<Status> statusList; //TODO : similarly add filters for Currency, CategoryID , CategoryName etc
    private String sortBy;

    @Override
    public boolean validate() {
        return ValidateInputRequestHelper.validateCatalogSearchQuery(this);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CatalogSearchQueryDtoBuilder {}
}
