package com.eshop.app.filters;

import com.eshop.app.common.constants.Status;
import com.eshop.app.constants.SearchConstants;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

public class ProductStatusFilter extends ESFilter {

    @Override
    public BoolQueryBuilder buildQuery(final CatalogSearchQueryDto dto) {
        final BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        for(Status next : dto.getStatusList()) {
            queryBuilder.should(
                    QueryBuilders.multiMatchQuery(next.getValue().toUpperCase())
                            .field(SearchConstants.PRODUCT_STATUS_PATH)
                            .field(SearchConstants.PRODUCT_CATEGORY_STATUS_PATH)
            );
        }
        return queryBuilder;
    }

    @Override
    public CatalogSearchQueryDto ifFilterPresent(final CatalogSearchQueryDto dto) {
        List<Status> statusList = dto.getStatusList();
        if(ObjectUtils.isEmpty(dto.getStatusList())) {
            statusList.add(Status.ACTIVE);
            statusList.add(Status.INACTIVE);
            statusList.add(Status.DELETED);
        }
        dto.setStatusList(statusList);
        return dto;
    }

}
