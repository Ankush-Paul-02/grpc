package com.paul.stock_trading_client.service;

import com.paul.grpc.StockRequest;
import com.paul.grpc.StockResponse;
import com.paul.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockClientService {

    //    private final StockTradingServiceGrpc.StockTradingServiceBlockingStub serviceBlockingStub;
    private final StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;

//    public StockResponse getStockPrice(String stockSymbol) {
//        StockRequest request = StockRequest.newBuilder()
//                .setStockSymbol(stockSymbol)
//                .build();
//
//        return serviceBlockingStub.getStockPrice(request);
//    }

    public void subscribeStockPrice(String symbol) {
        StockRequest request = StockRequest.newBuilder()
                .setStockSymbol(symbol)
                .build();

        stockTradingServiceStub.subscribeStockPrice(request, new StreamObserver<StockResponse>() {
            @Override
            public void onNext(StockResponse stockResponse) {
                System.out.println("Stock Price Update: " + stockResponse.getStockSymbol() + ", Price: " + stockResponse.getPrice() + ", Time: " + stockResponse.getTimestamp());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Stock price stream live update completed.");
            }
        });
    }
}
