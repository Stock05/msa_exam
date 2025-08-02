package com.sparta.msa_exam.gateway.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class CustomLoadBalancerConfiguration {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> weightedLoadBalancer(
            Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new WeightedLoadBalancer(loadBalancerClientFactory.getLazyProvider(serviceId, ServiceInstanceListSupplier.class), serviceId);
    }
}