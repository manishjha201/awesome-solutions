package com.eshop.app.models.resp;

import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;



@Data
@Builder
@JsonDeserialize (builder = SearchCatalogResponse.SearchCatalogResponseBuilder.class)
public class SearchCatalogResponse {
    private Page<Product> response;
    private CatalogSearchQueryDto request;
    @JsonPOJOBuilder(withPrefix = "")
    public static class SearchCatalogResponseBuilder {}

    public boolean isEmpty() {
        return ObjectUtils.isEmpty(response) || response.isEmpty();
    }
}
