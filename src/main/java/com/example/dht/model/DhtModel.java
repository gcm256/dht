package com.example.dht.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DhtModel {
    private ConcurrentHashMap<String, String> ht = null;
    private ConcurrentSkipListSet<String> nodes = null;

    public DhtModel() {
        ht = new ConcurrentHashMap<>();
        nodes = new ConcurrentSkipListSet<>();
    }

    public String get(String key) {
        return Optional.ofNullable(ht.get(key))
                       .orElseGet(() -> getFromNodes(key).orElse(null));
    }

    public void set(String key, String value) {
        ht.put(key, value);
        Runnable job = () -> {for (String nodeUrl : nodes) setToNode(key, value, nodeUrl);};
        Executors.newSingleThreadExecutor().submit(job);
    }

    public void addNode(String host, String port) {
        nodes.add(host+":"+port);
        RestTemplate t;
        ObjectMapper objectMapper;
    }

    private Optional<String> getFromNodes(String key) {
        for (String nodeUrl : nodes) {
            String value = getFromNode(key, nodeUrl);
            if (value != null) return Optional.of(value);
        }
        return Optional.empty();
    }

    private String getFromNode(String key, String nodeUrl) {
        String uri = "http://"+nodeUrl+"/get/{keyName}";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        SimpleClientHttpRequestFactory rf =
                (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        rf.setReadTimeout(1 * 1000);
        rf.setConnectTimeout(1 * 1000);
        ResponseEntity<String> responseEntity = null;
        String result = null;
        try {
            responseEntity = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(null), String.class, key);
            result = responseEntity.getBody();
        }
        catch (HttpStatusCodeException e) {

        }
        catch (RestClientException e) {

        }
        return result;
    }

    private void setToNode(String key, String value, String nodeUrl) {
        String uri = "http://"+nodeUrl+"/set/{keyName}";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        SimpleClientHttpRequestFactory rf =
                (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        rf.setReadTimeout(1 * 1000);
        rf.setConnectTimeout(1 * 1000);
        try {
            restTemplate.exchange(uri, HttpMethod.POST, getHttpEntity(value), String.class, key);
        }
        catch (HttpStatusCodeException e) {

        }
        catch (RestClientException e) {

        }
    }

    private static HttpEntity<String> getHttpEntity(String body) {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<String>(body, headers);
    }
}
