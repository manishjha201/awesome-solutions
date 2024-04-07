package com.eshop.app.consumer.models;

import com.eshop.app.common.models.EShoppingChangeEvent;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.Optional;

@Builder
@Data
public class ProcessedFeedOutput implements Serializable {
    private static final long serialVersionUID = 1245633341925210100L;
    private String responseMsg;
    private String responseCode;
    private Boolean isSuccess;
    private long timeTakenToProcess;
    private Optional<EShoppingChangeEvent> originalMsg;
}
