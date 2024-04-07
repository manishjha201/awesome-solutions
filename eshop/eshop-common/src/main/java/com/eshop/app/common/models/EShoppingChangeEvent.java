package com.eshop.app.common.models;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

/**
 *
 */
@Builder
@Data
public class EShoppingChangeEvent implements Serializable {
    private static final long serialVersionUID = 2406689879509166582L;
    private ProductChangeEvent productChangeEvent;
    private ProductChangeMetaData productChangeMetaData;
}
