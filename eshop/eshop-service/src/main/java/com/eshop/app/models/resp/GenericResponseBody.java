package com.eshop.app.models.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponseBody<T> implements Serializable {
    private static final long serialVersionUID = 6922665450495701030L;

    private T response;
    private ResultInfo resultInfo;
}
