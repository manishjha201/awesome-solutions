package com.eshop.app.exception.services.catalogue;

import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.exception.ResourceNotFoundException;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.models.resp.SearchCatalogResponse;

public interface ICatalogSearchService {
    SearchCatalogResponse getCatalogDetails(CatalogSearchQueryDto dto, String loginId) throws ResourceNotFoundException, BusinessException;
}
