package com.eshop.app.common.models.kafka;

import com.eshop.app.common.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Product.ProductBuilder.class)
public class Product implements Serializable {
    private static final long serialVersionUID = -281542741856338210L;
    private Long id;
    private String refID;
    private String title;
    private String name;
    private String description;
    private Double price;
    private String currency;
    private Inventory inventory;
    private Status status;
    private Long categoryId;
    private String imageUrl;
    private Long tenantId;
    private Boolean isUpdatedToES;
    private int version;

    // Private constructor to enforce use of Builder
    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.refID = builder.refID;
        this.title = builder.title;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.currency = builder.currency;
        this.inventory = builder.inventory;
        this.status = builder.status;
        this.categoryId = builder.categoryId;
        this.imageUrl = builder.imageUrl;
        this.tenantId = builder.tenantId;
        this.isUpdatedToES = builder.isUpdatedToES;
        this.version = builder.version;
    }

    @JsonPOJOBuilder(withPrefix = "with", buildMethodName = "build")
    public static class ProductBuilder {
        private Long id;
        private String refID;
        private String title;
        private String name;
        private String description;
        private Double price;
        private String currency;
        private Inventory inventory;
        private Status status;
        private Long categoryId;
        private String imageUrl;
        private Long tenantId;
        private Boolean isUpdatedToES;
        private int version;

        public ProductBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withRefID(String refID) {
            this.refID = refID;
            return this;
        }

        public ProductBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public ProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public ProductBuilder withInventory(Inventory inventory) {
            this.inventory = inventory;
            return this;
        }

        public ProductBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public ProductBuilder withCategoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public ProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductBuilder withTenantId(Long tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public ProductBuilder withIsUpdatedToES(Boolean isUpdatedToES) {
            this.isUpdatedToES = isUpdatedToES;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
