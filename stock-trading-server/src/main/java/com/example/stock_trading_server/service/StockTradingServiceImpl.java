package com.example.stock_trading_server.service;

import com.example.stock_trading_server.entity.Stock;
import com.example.stock_trading_server.entity.repository.StockRepository;
import com.paul.grpc.StockRequest;
import com.paul.grpc.StockResponse;
import com.paul.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Optional;

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
}
