package com.paul.stock_trading_client.service;

import com.paul.grpc.StockRequest;
import com.paul.grpc.StockResponse;
import com.paul.grpc.StockTradingServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockClientService {

    private final StockTradingServiceGrpc.StockTradingServiceBlockingStub serviceBlockingStub;

    public StockResponse getStockPrice(String stockSymbol) {
        StockRequest request = StockRequest.newBuilder()
                .setStockSymbol(stockSymbol)
                .build();

        return serviceBlockingStub.getStockPrice(request);
    }
}
