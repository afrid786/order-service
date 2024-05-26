package com.codedecode.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMsApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestClient.Builder getRestTemplate() {
		return RestClient.builder();
	}

}
