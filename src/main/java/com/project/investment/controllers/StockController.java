package com.project.investment.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.investment.controllers.dto.CreateStockDTO;
import com.project.investment.entities.Stock;
import com.project.investment.services.StockService;

@RestController
@RequestMapping("v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody CreateStockDTO body) {
        stockService.createStock(body);

        return ResponseEntity.ok().build();
    }

}
