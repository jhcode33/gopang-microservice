package com.gopang.itemserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
//@EnableFeignClients
public class ItemserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemserverApplication.class, args);
    }
}
