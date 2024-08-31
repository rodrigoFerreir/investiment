package com.project.investment.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.investment.entities.AccountStock;
import com.project.investment.entities.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
    List<AccountStock> findByAccountId(UUID accountId);
}
