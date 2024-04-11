package com.eshop.app.mapper.resp;

import com.eshop.app.common.constants.HttpResponseStatus;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.models.resp.ProductDetailResponse;
import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.models.resp.ProductsResponse;

import java.util.ArrayList;
import java.util.List;

public class ResponseMapper {

    public static ProductDetailResponse mapProductDetailResponse(Product product) {
        ProductDetailResponse resp = ProductDetailResponse.builder().response(product).build();
        if(product.getIsUpdatedToES()) {
            resp.setLastUpdatedStatus(HttpResponseStatus.UPDATED);
        } else {
            resp.setLastUpdatedStatus(HttpResponseStatus.ACCEPTED);
        }
        return resp;
    }

    public static ProductsResponse mapGetProductDetailResponse(List<Product> products) {
        List<ProductResp> resps = new ArrayList<>();
        for(Product next : products) {
            ProductResp resp = ProductResp.builder().response(next).build();
            if (next.getIsUpdatedToES()) {
                resp.setStatus(HttpResponseStatus.UPDATED);
            } else {
                resp.setStatus(HttpResponseStatus.ACCEPTED);
            }
            resps.add(resp);
        }
        return ProductsResponse.builder().response(resps).build();
    }
}
