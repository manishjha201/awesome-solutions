package com.eshop.app.controllers;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.constants.Status;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.models.resp.GenericResponseBody;
import com.eshop.app.models.resp.ResultInfo;
import com.eshop.app.models.resp.SearchCatalogResponse;
import com.eshop.app.services.catalog.IInventoryCountService;
import com.eshop.app.services.catalog.ICatalogSearchService;
import com.eshop.app.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Provides capability to search Catalog using configurable filters.
 * return pageable products.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogSearchController {

    private final ICatalogSearchService catalogSearchService;
    private final IInventoryCountService inventoryCountService;

    @Autowired
    public CatalogSearchController(ICatalogSearchService searchCatalogueService, IInventoryCountService inventoryCountService) {
        this.catalogSearchService = searchCatalogueService;
        this.inventoryCountService = inventoryCountService;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchCatalogResponse> getCatalogInfo(
            @RequestParam(name = "query_search_key", required = false) @Valid final String querySearchKey,
            @RequestParam(name = "query_search_value", required = false) @Valid final String querySearchValue,
            @RequestParam(name = "status", required = false, defaultValue = "ACTIVE") @Valid List<Status> statuses,
            @RequestParam(name = "sort_by", required = false) @Valid String sortBy,
            @RequestParam(name = "page", defaultValue = "1") @Valid Integer pageNumber,
            @RequestParam(name = "page_size", defaultValue = "1") @Valid Integer pageSize,
            @RequestHeader(value = "loginId", required = false) String loginId) {
        CatalogSearchQueryDto dto = CatalogSearchQueryDto.builder()
                .searchKey(Utility.transformInput(querySearchKey))
                .searchValue(querySearchValue)
                .statusList(statuses)
                .sortBy(sortBy)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
        //TODO : CALL VALIDATION SERVICE
        log.info("Request received for API={} with values : {}", dto);
        GenericResponseBody<SearchCatalogResponse> body = new GenericResponseBody<>();
        SearchCatalogResponse resp = catalogSearchService.getCatalogDetails(dto, loginId);
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity(body, HttpStatus.OK);
    }

}
