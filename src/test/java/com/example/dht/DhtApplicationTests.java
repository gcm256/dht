package com.example.dht;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DhtApplicationTests {
    @Autowired DhtController dhtController;

	@Test
	void contextLoads() throws Exception {
        Assertions.assertThat(dhtController).isNotNull();
	}

}
