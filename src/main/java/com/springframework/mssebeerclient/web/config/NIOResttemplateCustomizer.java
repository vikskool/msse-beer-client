package com.springframework.mssebeerclient.web.config;

import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.NHttpClientConnection;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOEventDispatch;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//@Component
public class NIOResttemplateCustomizer implements RestTemplateCustomizer {

    private final Integer maxTotalConnections;
    private final Integer defaultMasxPerRoute;
    private final Integer connectionRequestTimeout;
    private final Integer sockectTimeout;
    private final Integer thredCount;

    public NIOResttemplateCustomizer(@Value("${sfg.maxtotalconnections}") Integer maxTotalConnections,
                                     @Value("${sfg.defaultmaxperroute}") Integer defaultMasxPerRoute,
                                     @Value("${sfg.connectionrequesttimeout}") Integer connectionRequestTimeout,
                                     @Value("${sfg.sockettimeout}") Integer sockectTimeout,
                                     @Value("${sfg.threadcount}") Integer threadCount) {
        this.maxTotalConnections = maxTotalConnections;
        this.defaultMasxPerRoute = defaultMasxPerRoute;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.sockectTimeout = sockectTimeout;
        this.thredCount = threadCount;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {

        final DefaultConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().
                setConnectTimeout(connectionRequestTimeout).
                setIoThreadCount(thredCount).
                setSoTimeout(sockectTimeout).
                build());

        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioreactor);
        connectionManager.setDefaultMaxPerRoute(defaultMasxPerRoute);
        connectionManager.setMaxTotal(maxTotalConnections);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(connectionManager).build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }

    @Override
    public void customize(RestTemplate restTemplate){
        try{
            restTemplate.setRequestFactory(clientHttpRequestFactory());
        }catch (IOReactorException e){
            e.printStackTrace();
        }
    }
}
