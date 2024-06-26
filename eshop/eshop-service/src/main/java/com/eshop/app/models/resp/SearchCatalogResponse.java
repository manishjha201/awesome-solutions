package com.eshop.app.models.resp;

import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;


@Data
@Builder
@JsonDeserialize (builder = SearchCatalogResponse.SearchCatalogResponseBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchCatalogResponse extends HttpResponse {
    private static final long serialVersionUID = -4966133033063306971L;
    private Page<Product> response;
    private CatalogSearchQueryDto request;
    @JsonPOJOBuilder(withPrefix = "")
    public static class SearchCatalogResponseBuilder {}

    public boolean isEmpty() {
        return ObjectUtils.isEmpty(response) || response.isEmpty();
    }
}
