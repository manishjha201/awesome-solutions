package com.eshop.app.services;
import com.eshop.app.common.exceptions.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

@Service
public class DataService<T, K> {

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final Function<T, K> converter;
    private final  Map<Class<?>, String> apiUrls;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public DataService(RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate, Function<T, K> converter, Map<Class<?>, String> apiUrls) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
        this.converter = converter;
        this.apiUrls = apiUrls;
    }

    public K processData(T data) {
        // T -> K
        return converter.apply(data);
    }

    public K createData(Object data, Class<K> responseType, Map<String, String> requestParams, HttpHeaders additionalHeaders) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiUrls.get(responseType));
        if (requestParams != null && !requestParams.isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                uriBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        URI uri = uriBuilder.build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(additionalHeaders);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(data, headers);
        ResponseEntity<K> response = restTemplate.exchange(uri, HttpMethod.POST, entity, responseType);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new BusinessException("Failed to create data. Status code: " + response.getStatusCode());
        }
    }

    public K updateData(String namespace, String key, Object data, Class<K> responseType, Map<String, String> requestParams, HttpHeaders additionalHeaders) {
        String fullKey = buildFullKey(namespace, key);
        // Clean data from Redis
        redisTemplate.delete(fullKey);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiUrls.get(responseType)).path(key);;
        if (requestParams != null && !requestParams.isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                uriBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        URI uri = uriBuilder.build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(additionalHeaders);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(data, headers);
        ResponseEntity<K> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, responseType);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new BusinessException("Failed to create data. Status code: " + response.getStatusCode());
        }
    }


    public K getData(String namespace, String key, Class<K> responseType, Map<String, String> queryParams, HttpHeaders additionalHeaders, long ttl) throws JsonProcessingException {
       String fullKey = buildFullKey(namespace, key);
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedData = ops.get(fullKey);
        if (cachedData != null && responseType.isInstance(cachedData)) {
            return objectMapper.readValue(cachedData, responseType);
        }
        URI uri = buildUri(apiUrls.get(responseType), key, queryParams);
        HttpEntity<?> entity = new HttpEntity<>(additionalHeaders);
        ResponseEntity<K> response = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);
        if (response != null && response.getBody() != null) {
            String json = objectMapper.writeValueAsString(response.getBody()); ;
            ops.set(fullKey, json, Duration.ofSeconds(ttl));
            return response.getBody();
        }
        return null;
    }

    private URI buildUri(String baseUrl, String key, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + key);
        queryParams.forEach(builder::queryParam);
        return builder.build().toUri();
    }

    private String buildFullKey(String namespace, String key) {
        return namespace + ":" + key;
    }
}