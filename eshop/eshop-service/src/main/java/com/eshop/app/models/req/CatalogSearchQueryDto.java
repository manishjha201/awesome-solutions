package com.eshop.app.models.req;

import com.eshop.app.common.constants.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = CatalogSearchQueryDto.CatalogSearchQueryDtoBuilder.class)
public class CatalogSearchQueryDto {
    private String searchKey;
    private String searchValue;
    private int pageNumber;
    private int pageSize;
    private List<Status> statusList; //TODO : similarly add filters for Currency, CategoryID , CategoryName etc
    private String sortBy;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CatalogSearchQueryDtoBuilder {}
}
