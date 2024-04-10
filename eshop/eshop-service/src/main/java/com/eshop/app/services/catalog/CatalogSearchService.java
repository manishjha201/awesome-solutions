package com.eshop.app.services.catalog;

import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.exception.ResourceNotFoundException;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.models.resp.SearchCatalogResponse;
import com.eshop.app.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public final class CatalogSearchService implements ICatalogSearchService {
    private final ISearchService searchService;

    @Autowired
    public CatalogSearchService(ISearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public SearchCatalogResponse getCatalogDetails( CatalogSearchQueryDto dto, String loginId) throws ResourceNotFoundException, BusinessException {
        log.info("fetching detail from Es data store for loginUser : {} ", loginId);
        Pageable pageable = PageRequest.of(dto.getPageNumber(), dto.getPageSize(), Sort.by(Sort.Direction.ASC, "productID")); //TODO : Customise
        Page<Product> resultSet = searchService.searchByCustomQueryBuilder(dto, pageable);
        return prepareSearchCatalogResponse(resultSet, dto);
    }

    private SearchCatalogResponse prepareSearchCatalogResponse(Page<Product> resultSet, CatalogSearchQueryDto dto) {
       try {
            return Utility.responseMapperFromSearchResp(resultSet, dto);
        } catch(RuntimeException e) {
            log.error("Error occurred in mapping resultSet to output response {} ", resultSet, e);
            throw new BusinessException(e.getMessage());
        }
    }

}
