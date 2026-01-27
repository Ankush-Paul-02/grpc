package com.paul.stock_trading_client;

import com.paul.stock_trading_client.service.StockClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class StockTradingClientApplication implements CommandLineRunner {

    private final StockClientService service;

    public static void main(String[] args) {
        SpringApplication.run(StockTradingClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("GRPC client response: " + service.getStockPrice("AMZN"));
    }
}
