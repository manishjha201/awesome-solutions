package com.eshop.app.controllers;

import com.eshop.app.services.catalogue.IInventoryCountService;
import com.eshop.app.services.catalogue.ISearchCatalogueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class CatalogueSearchController {

    private final ISearchCatalogueService searchCatalogueService;
    private final IInventoryCountService inventoryCountService;

    @Autowired
    public CatalogueSearchController(ISearchCatalogueService searchCatalogueService, IInventoryCountService inventoryCountService) {
        this.searchCatalogueService = searchCatalogueService;
        this.inventoryCountService = inventoryCountService;
    }


}
