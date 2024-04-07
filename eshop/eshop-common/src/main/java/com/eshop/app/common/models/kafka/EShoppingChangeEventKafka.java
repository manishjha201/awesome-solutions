package com.eshop.app.common.models.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EShoppingChangeEventKafka implements Serializable {
    private static final long serialVersionUID = -2325329761371087751L;
    private String dataschema;
    private String data;
}
