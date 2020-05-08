package com.example.dht;

import com.example.dht.model.DhtModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
public class DhtControllerTests {
    //@Autowired
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testHello() throws Exception {
        restTemplate = new RestTemplate();
        Assertions.assertThat(this.restTemplate
                                      .getForObject("http://localhost:"
                                                            + port
                                                            + "/hello/DummyName"
                                              , String.class))
                .isEqualTo("Hello, DummyName!\n");
    }

    @Test
    public void testGetEmptyDht() throws Exception {
        restTemplate = new RestTemplate();
        Assertions.assertThat(this.restTemplate
                                      .getForObject("http://localhost:"
                                                            + port
                                                            + "/get/key1"
                                              , String.class))
                .isNull();
    }

    //@Test
    public void testGetAfterSet() throws Exception {
        restTemplate = new RestTemplate();
        String key = "key1", value = "value1";
        String setUri = "http://localhost:" + port + "/set/{keyName}";
        String getUri = "http://localhost:" + port + "/get/"+key;
        this.restTemplate.exchange(setUri, HttpMethod.POST, getHttpEntity(value), String.class, key);
        Assertions.assertThat(this.restTemplate.getForObject(getUri, String.class))
                .isEqualTo(value);
    }

    @Test
    public void testSet() throws Exception {
        restTemplate = new RestTemplate();
        String key = "key1", value = "value1";
        String setUri = "http://localhost:" + port + "/set/{keyName}";
        Assertions.assertThat(this.restTemplate
                                      .exchange(setUri,
                                              HttpMethod.POST,
                                              getHttpEntity(value), String.class, key)
                                      .getBody()).isEqualTo("OK\n");
    }

    private static HttpEntity<String> getHttpEntity(String body) {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(body, headers);
    }
}
