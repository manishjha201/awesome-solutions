package com.eshop.app.mapper.resp;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.constants.HttpResponseStatus;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.common.entities.rdbms.Tenant;
import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.models.resp.CreateProductInfoResponse;
import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.models.resp.TenantResp;

import java.util.List;
import java.util.Optional;

public class ResponseMapper {

    public static ProductResp mapProductDetailResponse(Product product) {
        ProductResp resp = new ProductResp();
        resp.setProduct(product.build());
        if(product.getIsUpdatedToES()) {
            resp.setResponseStatus(HttpResponseStatus.UPDATED.toString());
        } else {
            resp.setResponseStatus(HttpResponseStatus.ACCEPTED.toString());
        }
        return resp;
    }

    public static ProductResp mapGetProductDetailResponse(List<Product> products) {
        for (Product next : products) {
            ProductResp productResp = new ProductResp();
            productResp.setProduct(next.build());
            if (next.getIsUpdatedToES()) {
                productResp.setResponseStatus(HttpResponseStatus.UPDATED.toString());
            } else {
                productResp.setResponseStatus(HttpResponseStatus.ACCEPTED.toString());
            }
            return productResp;
        }
        throw new BusinessException(EShopResultCode.NOT_FOUND.getResultCode());
    }

    public static Optional<TenantResp> mapGetTenantDetailResponse(List<Tenant> tenants) {
        for (Tenant next : tenants) {
            TenantResp tenantResp = new TenantResp();
            return Optional.of(tenantResp);
        }
        throw new BusinessException(EShopResultCode.NOT_FOUND.getResultCode());
    }
}
