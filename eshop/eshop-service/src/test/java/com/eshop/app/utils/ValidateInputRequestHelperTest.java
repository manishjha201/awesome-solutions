package com.eshop.app.utils;

import com.eshop.app.common.constants.CatalogSearchKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateInputRequestHelperTest {


    private String safeText;
    private String unsafeText;

    @BeforeEach
    public void setUp() {
        safeText = "This is a safe text 123.";
        unsafeText = "DROP TABLE Students; --";
    }

    @Test
    public void testIsSafeTest() {
        assertTrue(ValidateInputRequestHelper.isSafeText(safeText));
        assertFalse(ValidateInputRequestHelper.isSafeText(unsafeText));
    }

    @Test
    public void isValidKeyForCatalogSearchQueryTest() {
        assertTrue(ValidateInputRequestHelper.isValidKeyForCatalogSearchQuery(CatalogSearchKey.PRODUCT_ID.getLabel()));
        assertFalse(ValidateInputRequestHelper.isValidKeyForCatalogSearchQuery(unsafeText));
    }


}
