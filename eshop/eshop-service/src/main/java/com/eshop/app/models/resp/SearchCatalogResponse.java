package com.eshop.app.models.resp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize (builder = SearchCatalogResponse.SearchCatalogResponseBuilder.class)
public class SearchCatalogResponse {
    private List<SearchCatalogRespModel> searchCatalogRespList;
    private int page;
    private int pageSize;
    private int totalPages;
    private int totalRecords;
    private int maxRecords;

    @JsonPOJOBuilder(withPrefix = "")
    public static class SearchCatalogResponseBuilder {}
}
