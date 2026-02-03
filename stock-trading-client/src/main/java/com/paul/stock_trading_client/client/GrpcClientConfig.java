package com.paul.stock_trading_client.client;

import io.grpc.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;
import com.paul.grpc.StockTradingServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @Bean
    public StockTradingServiceGrpc.StockTradingServiceStub stockAsyncStub(
            GrpcChannelFactory channelFactory
    ) {
        Channel channel = channelFactory.createChannel("default-channel");

        return StockTradingServiceGrpc.newStub(channel);
    }
}
