package com.eshop.app.utils;

import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.models.resp.SearchCatalogResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    private List<Product> allProducts;
    private Pageable firstPageWithTenItems;
    private Pageable secondPageWithTwentyItems;
    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder().id(UUID.randomUUID().toString()).productID("0").title("title 0").name("name 0").build();
        allProducts = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Product next = new Product();
            next.setTitle("title " + i);
            next.setName("product " + i);
            next.setProductID(String.valueOf(i * 100));
            next.setId(UUID.randomUUID().toString());
            allProducts.add(next);
        }
        firstPageWithTenItems = PageRequest.of(0, 10);
        secondPageWithTwentyItems = PageRequest.of(1, 20);
    }


    @Test
    public void testEscapeSpecialCharacters() {
        String testQuery = "eco-friendly (products) [2024] -sale! *query* ?search: \\escape";
        String expected = "eco\\-friendly \\(products\\) \\[2024\\] \\-sale\\! \\*query\\* \\?search\\: \\\\escape";
        String result = Utility.escapeSpecialCharacters(testQuery);
        assertEquals(expected, result, "The escapeSpecialCharacters method did not properly escape special characters.");
    }

    @Test
    public void testResponseMapperWithEmptyProducts() {
        Page<Product> emptyProducts = new PageImpl<>(Collections.emptyList());
        SearchCatalogResponse response = Utility.responseMapperFromSearchResp(emptyProducts, CatalogSearchQueryDto.builder().build());
        assertNotNull(response, "Response should not be null.");
        assertTrue(response.isEmpty(), "Response should be empty when products are empty.");
    }

    @Test
    public void testResponseMapperWithNonEmptyProducts() {
        List<Product> productList = List.of(product);
        Page<Product> products = new PageImpl<>(productList);
        SearchCatalogResponse response = Utility.responseMapperFromSearchResp(products, CatalogSearchQueryDto.builder().build());
        assertNotNull(response, "Response should not be null.");
        assertFalse(response.isEmpty(), "Response should contain products.");
        assertEquals(1L, response.getResponse().getTotalElements(), "Response should contain exactly 1 product.");
    }

    @Test
    public void testListToPage() {
        Page<Product> firstPage = Utility.listToPage(allProducts, firstPageWithTenItems);
        Page<Product> secondPage = Utility.listToPage(allProducts, secondPageWithTwentyItems);
        assertEquals(10, firstPage.getContent().size(), "First page should contain 10 items.");
        assertEquals(50, firstPage.getTotalElements(), "Total elements should be 50.");
        assertEquals(20, secondPage.getContent().size(), "Second page should contain 20 items.");
        assertEquals(50, secondPage.getTotalElements(), "Total elements should be 50.");
        assertEquals("product 1", firstPage.getContent().get(0).getName(), "First product of the first page should match.");
        assertEquals("product 21", secondPage.getContent().get(0).getName(), "First product of the second page should match.");
    }

    @Test
    public void testEnhancedInput() {
        assertEquals("a", Utility.enhanceInput("a"));
        assertEquals("Eco\\-friendly", Utility.enhanceInput("Eco-friendly"));
    }

    @Test
    public void testTransformInput() {
        assertEquals("a", Utility.transformInput("A"));
        assertEquals("a", Utility.enhanceInput("a"));
    }
}
