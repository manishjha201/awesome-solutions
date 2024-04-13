package com.eshop.app.consumer.services;

import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.common.entities.rdbms.Category;
import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.common.repositories.nosql.es.ProductRepository;
import com.eshop.app.common.repositories.rdbms.master.CategoryRepository;
import com.eshop.app.consumer.models.ProductUpdateReqDTO;
import com.eshop.app.consumer.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.OutputStream;

@Slf4j
@Service
public class EsDataIngestionService implements IEsDataIngestionService {

    @Autowired
    private CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Autowired
    public EsDataIngestionService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean deleteProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean create(EShoppingChangeEvent changeEvent) {
        Optional<Category> category = categoryRepository.findById(changeEvent.getProductChangeEvent().getCurrentValue().getCategoryId());
        Product toSave = Utility.getProduct(changeEvent, category.get());
        Product result = saveProduct(toSave);
        String refId = result.getId();
        ProductUpdateReqDTO dto = Utility.buildProductUpdateReqDTO(refId, changeEvent);
        saveProductAtDb(dto, changeEvent.getProductChangeEvent().getCurrentValue().getId().toString());
        return false;
    }

    @Override
    public boolean update(EShoppingChangeEvent changeEvent) {
        Optional<Category> category = categoryRepository.findById(changeEvent.getProductChangeEvent().getCurrentValue().getCategoryId());
        Product toSave = Utility.getProductForUpdate(changeEvent, category.get(), changeEvent.getProductChangeEvent().getCurrentValue().getRefID());
        Product result = saveProduct(toSave);
        String refId = result.getId();
        ProductUpdateReqDTO dto = Utility.buildProductUpdateReqDTO(refId, changeEvent);
        saveProductAtDb(dto, changeEvent.getProductChangeEvent().getCurrentValue().getId().toString());
        return true;
    }

    @Override
    public boolean delete(EShoppingChangeEvent changeEvent) {
        return deleteProductById(changeEvent.getProductChangeEvent().getCurrentValue().getRefID());
    }

    public void saveProductAtDb(ProductUpdateReqDTO productReq, String productId) {
        String endpoint = "localhost:8080/internal/data/v1/products" + productId; //TODO : make it configurable
        try {
            String response = callPutMethod(endpoint, productReq);
            log.info("Response from API: {} ", response);
        } catch (IOException e) {
            log.info(" Exception occured : {}", e);
        }
    }

    public static String callPutMethod(String endpoint, ProductUpdateReqDTO productReq) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(productReq);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new IOException("API call request failed with HTTP error code: " + responseCode);
        }
    }

}
