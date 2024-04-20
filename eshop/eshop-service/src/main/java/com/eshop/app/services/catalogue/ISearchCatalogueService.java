package com.eshop.app.services.catalogue;

import com.eshop.app.common.entities.nosql.Catalog;
import org.springframework.data.domain.Page;

public interface ISearchCatalogueService {

    Page<Catalog> findAllCatalogs(int pageNo, int pageSize);
}
