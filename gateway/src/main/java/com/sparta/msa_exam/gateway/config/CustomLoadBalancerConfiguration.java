package com.sparta.msa_exam.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Slf4j
public class CustomLoadBalancerConfiguration {

    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {
        return ServiceInstanceListSupplier.builder()
                .withDiscoveryClient()
                .withWeighted(this::setWeight)
                .build(context);
    }

    public int setWeight(ServiceInstance instance){
        int port = instance.getPort();
        log.info("port is {}", port);
        if (port == 19093) {
            return 70; // 가중치 70
        } else if (port == 19094) {
            return 30; // 가중치 30
        }
        return 1; // 기본 가중치
    }
}