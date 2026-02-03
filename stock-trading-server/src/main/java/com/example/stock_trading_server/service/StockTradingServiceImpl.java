package com.example.stock_trading_server.service;

import com.example.stock_trading_server.entity.Stock;
import com.example.stock_trading_server.entity.repository.StockRepository;
import com.paul.grpc.StockRequest;
import com.paul.grpc.StockResponse;
import com.paul.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@GrpcService
@RequiredArgsConstructor
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {

    private final StockRepository stockRepository;

    @Override
    public void getStockPrice(
            StockRequest request,
            StreamObserver<StockResponse> responseObserver
    ) {
        String stockSymbol = request.getStockSymbol();
        Optional<Stock> optionalStock = stockRepository.findByStockSymbol(stockSymbol);
        if (optionalStock.isEmpty()) {
            throw new RuntimeException("Stock not found!");
        }
        Stock stock = optionalStock.get();

        StockResponse stockResponse = StockResponse.newBuilder()
                .setStockSymbol(stock.getStockSymbol())
                .setPrice(stock.getPrice())
                .setTimestamp(stock.getLastupdated().toString())
                .build();

        responseObserver.onNext(stockResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void subscribeStockPrice(
            StockRequest request,
            StreamObserver<StockResponse> responseObserver
    ) {
        String stockSymbol = request.getStockSymbol();
        Random random = new Random();

        try {
            for (int i = 0; i < 10; i++) {

                StockResponse stockResponse = StockResponse.newBuilder()
                        .setStockSymbol(stockSymbol)
                        .setPrice(random.nextDouble(200))
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(stockResponse);

                TimeUnit.SECONDS.sleep(1);
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
