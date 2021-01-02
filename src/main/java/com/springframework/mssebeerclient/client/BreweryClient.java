package com.springframework.mssebeerclient.client;


import com.springframework.mssebeerclient.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Controller
@ConfigurationProperties(value = "beer.service", ignoreUnknownFields = false)
public class BreweryClient {

    private String apiHost;

    public RestTemplate restTemplate;

    private static final String BEER_PATH_V1 = "/api/v1/beer/";

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public BeerDto getBeerById(UUID uuid) {
        return this.restTemplate.getForObject(this.apiHost + BEER_PATH_V1 + uuid.toString(), BeerDto.class);
    }

    public URI createNewBeer(BeerDto beerDto){

        return this.restTemplate.postForLocation(this.apiHost + BEER_PATH_V1 , beerDto);
    }

    public void updateBeer(UUID uuid, BeerDto beerDto){
        this.restTemplate.put(this.apiHost + BEER_PATH_V1 + uuid.toString(),beerDto);
    }

    public void deleteBeer(UUID uuidId){
        this.restTemplate.delete(this.apiHost + BEER_PATH_V1 + uuidId);

    }


}
