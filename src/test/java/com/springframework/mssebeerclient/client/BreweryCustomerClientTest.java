package com.springframework.mssebeerclient.client;

import com.springframework.mssebeerclient.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BreweryCustomerClientTest {

    @Autowired
    BreweryCustomerClient client;

    @Test
    void getCustomerById() {
        UUID customerId = UUID.randomUUID();
        CustomerDto customer = this.client.getCustomerById(customerId);

        assertNotNull(customer);
    }

    @Test
    void createCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .customerName("Vikram Sinha")
                .build();
        URI uri = this.client.createCustomer(customerDto);
        assertNotNull(uri);

        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        UUID custometId = UUID.randomUUID();
        CustomerDto customerDto = CustomerDto.builder()
                .id(custometId)
                .customerName("Jeff")
                .build();

        this.client.updateCustomer(custometId, customerDto);
    }

    @Test
    void deleteCustomer() {
        UUID customerId =  UUID.randomUUID();
        this.client.deleteCustomer(customerId);
    }
}