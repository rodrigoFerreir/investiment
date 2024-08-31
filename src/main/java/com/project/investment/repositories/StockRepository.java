package com.project.investment.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.investment.entities.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Stock findByCode(String stockCode);
}
