package com.eshop.app.models.resp;

import com.eshop.app.common.constants.EShopResultCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultInfo implements Serializable {
    private static final long serialVersionUID = 2603988135115524524L;

    private String code;
    private String status;
    private String message;

    @Builder
    public ResultInfo(EShopResultCode resultCode) {
        this.code = resultCode.getResultCode();
        this.message = resultCode.getMessage();
        this.status = resultCode.getStatus();
    }
}
