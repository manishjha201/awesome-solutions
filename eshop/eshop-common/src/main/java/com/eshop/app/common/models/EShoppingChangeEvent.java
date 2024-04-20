package com.eshop.app.common.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

/**
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = EShoppingChangeEvent.EShoppingChangeEventBuilder.class)
@JsonSerialize
@ToString
public class EShoppingChangeEvent implements Serializable {
    private static final long serialVersionUID = 2406689879509166582L;
    private ProductChangeEvent productChangeEvent;
    private ProductChangeMetaData productChangeMetaData;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EShoppingChangeEventBuilder {}
}
