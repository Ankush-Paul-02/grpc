package com.example.stock_trading_server.entity.repository;

import com.example.stock_trading_server.entity.Stock;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<@NonNull Stock, @NonNull Long> {
    Optional<Stock> findByStockSymbol(String stockSymbol);
}
