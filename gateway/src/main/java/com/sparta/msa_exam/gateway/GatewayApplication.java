package com.sparta.msa_exam.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import com.sparta.msa_exam.gateway.config.CustomLoadBalancerConfiguration;

@SpringBootApplication
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfiguration.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
