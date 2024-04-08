package com.eshop.app.models.resp;

import com.eshop.app.common.models.es.Product;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SearchCatalogRespModel implements Serializable {
    private static final long serialVersionUID = 8198783305842969671L;
    private String userId;
    private String name;
    private List<Product> products;
}
