package com.springframework.mssebeerclient.client;

import com.springframework.mssebeerclient.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "beer.service", ignoreUnknownFields = false)
public class BreweryCustomerClient {

    private static final String BEER_CUSTOMER_V1 = "/api/v1/customer/";

    private String apiHost;

    RestTemplate restTemplate;

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public BreweryCustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDto getCustomerById(UUID uuid){
        CustomerDto customerDto = this.restTemplate.getForObject(this.apiHost + BEER_CUSTOMER_V1 + uuid, CustomerDto.class);

        return customerDto;
    }

    public URI createCustomer(CustomerDto customerDto){
        URI uri = this.restTemplate.postForLocation(this.apiHost + BEER_CUSTOMER_V1, customerDto);
        return uri;
    }

    public void updateCustomer(UUID customerId, CustomerDto customerDto){
        this.restTemplate.put(this.apiHost + BEER_CUSTOMER_V1 + customerId, customerDto);
    }

    public void deleteCustomer(UUID customerId){
        this.restTemplate.delete(this.apiHost + BEER_CUSTOMER_V1 + customerId);
    }
}
