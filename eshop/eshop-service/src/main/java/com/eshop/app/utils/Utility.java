package com.eshop.app.utils;

import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.models.resp.SearchCatalogResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utility {

    public static String getErrorMsg(String message, String errorMessage) {
        return StringUtils.isEmpty(errorMessage) ? message : errorMessage;
    }

    public static String enhanceInput(String value) {
        return Utility.escapeSpecialCharacters(value.trim());
    }


    public static SearchCatalogResponse responseMapperFromSearchResp(Page<Product> products, CatalogSearchQueryDto dto) {
        if (ObjectUtils.isEmpty(products)) return SearchCatalogResponse.builder().build();
        return SearchCatalogResponse.builder()
                .response(products)
                .request(dto)
                .build();
    }

    public static Page<Product> listToPage(List<Product> productList, Pageable pageable) {
        int totalItems = productList.size();
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalItems);
        List<Product> sublist = productList.subList(start, end);
        return new PageImpl<>(sublist, pageable, totalItems);
    }

    public static String escapeSpecialCharacters(String queryString) {
        List<String> specialChars = List.of("\\", "+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^", "\"", "~", "*", "?", ":", "/");
        for (String specialChar : specialChars) {
            queryString = queryString.replace(specialChar, "\\" + specialChar);
        }
        return queryString;
    }

    public static String transformInput(String querySearchKey) {
        if(ObjectUtils.isEmpty(querySearchKey)) return null;
        return querySearchKey.toLowerCase().trim();
    }
}
