package com.springframework.mssebeerclient.web.client;


import com.springframework.mssebeerclient.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient sfgBreweryClient;

    @Test
    void getBeerById() {
        BeerDto beerDto = this.sfgBreweryClient.getBeerById(UUID.randomUUID());

        assertNotNull(beerDto);
    }

    @Test
    void createNewBeer(){
        BeerDto beerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Pale Ale")
                .beerStyle("PILSNER")
                .build();

        URI uri = this.sfgBreweryClient.createNewBeer(beerDto);
        System.out.println(uri);
    }

    @Test
    void updateBeer(){
        UUID uuid = UUID.randomUUID();
        BeerDto beerDto = BeerDto.builder()
                .id(uuid)
                .beerName("Pale Ale")
                .beerStyle("PILSNER")
                .build();

        this.sfgBreweryClient.updateBeer(uuid, beerDto);
    }

    @Test
    void deleteBeer(){
        this.sfgBreweryClient.deleteBeer(UUID.randomUUID());
    }
}