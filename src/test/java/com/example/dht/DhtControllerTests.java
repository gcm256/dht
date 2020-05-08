package com.example.dht;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
public class DhtControllerTests {
    //@Autowired
    private RestTemplate restTemplate;

    //@LocalServerPort
    private int port = 8080;

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
}
