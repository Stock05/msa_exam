package com.sparta.msa_exam.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class WeightedLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    private final String serviceId;

    public WeightedLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(this::getInstanceResponse);
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            log.warn("No instances available for service: {}", serviceId);
            return new EmptyResponse();
        }

        List<ServiceInstance> weightedList = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            int weight = getWeight(instance);
            for (int i = 0; i < weight; i++) {
                weightedList.add(instance);
            }
        }

        if (weightedList.isEmpty()) {
            log.warn("Weighted list is empty for service: {}. Falling back to random selection.", serviceId);
            int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
            return new DefaultResponse(instances.get(randomIndex));
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(weightedList.size());
        ServiceInstance instance = weightedList.get(randomIndex);
        log.info("Selected instance {} for service {}", instance.getUri(), serviceId);
        return new DefaultResponse(instance);
    }

    private int getWeight(ServiceInstance instance) {
        if (instance == null) {
            log.warn("ServiceInstance is null, using default weight");
            return 1;
        }
        int port = instance.getPort();
        log.info("Assigning weight for instance {} with port {}", instance.getInstanceId(), port);
        if (port == 19093) {
            return 70;
        } else if (port == 19094) {
            return 30;
        }
        return 1;
    }
}
