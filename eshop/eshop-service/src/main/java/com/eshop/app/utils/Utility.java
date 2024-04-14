package com.eshop.app.utils;

import com.eshop.app.common.constants.Currency;
import com.eshop.app.common.constants.Status;
import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.common.entities.rdbms.Cart;
import com.eshop.app.common.entities.rdbms.CartProduct;
import com.eshop.app.exception.ValidationException;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.resp.SearchCatalogResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utility {

    public static String getErrorMsg(String message, String errorMessage) {
        return StringUtils.isEmpty(errorMessage) ? message : errorMessage;
    }

    public static String enhanceInput(String value) {
        return Utility.escapeSpecialCharacters(value.trim());
    }


    public static SearchCatalogResponse responseMapperFromSearchResp(Page<Product> products, CatalogSearchQueryDto dto) {
        if (ObjectUtils.isEmpty(products)) return SearchCatalogResponse.builder().build();
        return SearchCatalogResponse.builder()
                .response(products)
                .request(dto)
                .build();
    }

    public static Page<Product> listToPage(List<Product> productList, Pageable pageable) {
        int totalItems = productList.size();
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalItems);
        List<Product> sublist = productList.subList(start, end);
        return new PageImpl<>(sublist, pageable, totalItems);
    }

    public static String escapeSpecialCharacters(String queryString) {
        List<String> specialChars = List.of("\\", "+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^", "\"", "~", "*", "?", ":", "/");
        for (String specialChar : specialChars) {
            queryString = queryString.replace(specialChar, "\\" + specialChar);
        }
        return queryString;
    }

    public static String transformInput(String querySearchKey) {
        if(ObjectUtils.isEmpty(querySearchKey)) return null;
        return querySearchKey.toLowerCase().trim();
    }


    //TODO : to remove
    public static void main(String[] args) {
        // Create an instance of ProductReqDTO for demonstration
        Integer nextValue = new Random().nextInt(100000);
        ProductReqDTO productReqDTO = ProductReqDTO.builder()
                .title("Example Title " + nextValue).name("Example Name " + nextValue).description("This is an example description " + nextValue)
                .price(new BigDecimal("19.99"))
                .currency(Currency.USD).inStock(true).quantity(100).reservedQuantity(0).minStockQuantity(20)
                .status(Status.ACTIVE)
                .categoryId(1L).imageUrl("//http:www.test.test/image" + nextValue + ".jpg")
                .tenantId(1L)
                .build();
        serializeProductReqDTOToFile(productReqDTO, "/Users/manishjha/Desktop/jsonFiles/productReqDTO.json");
    }

    public static void serializeProductReqDTOToFile(ProductReqDTO productReqDTO, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(filePath), productReqDTO);
            System.out.println("JSON file created: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateToken(String token) {
        if (StringUtils.isEmpty(token)) throw new ValidationException("Invalid token, login again");
        return true;
    }



    public static BigDecimal preparePaymentAmount(Cart cart) {
        BigDecimal grandTotal = new BigDecimal(0.00d).setScale(2, RoundingMode.HALF_DOWN);
        for (CartProduct next : cart.getItems()) {
            BigDecimal toPay = next.getProduct().getPrice().multiply(BigDecimal.valueOf(next.getQuantity()).setScale(2, RoundingMode.HALF_UP));
            grandTotal.add(toPay);
        }
        return grandTotal;
    }
}
