package com.project.investment.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.investment.controllers.dto.CreateStockDTO;
import com.project.investment.entities.Stock;
import com.project.investment.repositories.StockRepository;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {

        // DTO -> Entity

        var stock = new Stock(UUID.randomUUID(), createStockDTO.code(), createStockDTO.description());
        stockRepository.save(stock);
    }

}
