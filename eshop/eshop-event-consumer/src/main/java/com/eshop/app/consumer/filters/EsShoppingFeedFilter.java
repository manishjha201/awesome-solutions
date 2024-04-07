package com.eshop.app.consumer.filters;

import com.eshop.app.common.models.EShoppingChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EsShoppingFeedFilter implements IEsShoppingFeedFilter {

    @Override
    public EShoppingChangeEvent filter(EShoppingChangeEvent eShoppingChangeEvent) {
        //TODO : TBD
        return eShoppingChangeEvent;
    }

}
