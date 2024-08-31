package com.project.investment.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.investment.client.BrapiClient;
import com.project.investment.controllers.dto.AccountStockDTO;
import com.project.investment.entities.AccountStock;
import com.project.investment.entities.AccountStockId;
import com.project.investment.repositories.AccountRepository;
import com.project.investment.repositories.AccountStockRepository;
import com.project.investment.repositories.StockRepository;

@Service
public class AccountService {
    @Value("{TOKEN_API}") // adicionar token para realizar requests para api brapi
    private String token;

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private AccountStockRepository accountStockRepository;

    private BrapiClient brapiClient;

    public AccountService(
            AccountRepository accountRepository,
            StockRepository stockRepository,
            AccountStockRepository accountStockRepository,
            BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }

    public void associateStock(String accountId, AccountStockDTO body) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findByCode(body.code());

        var id = new AccountStockId(account.getId(), stock.getId());

        var entity_verify = accountStockRepository.findById(id);

        if (entity_verify.isPresent()) {
            entity_verify.get().appendQuantity(body.quantity());
            accountStockRepository.save(entity_verify.get());

        } else {
            var entity = new AccountStock(id, account, stock, body.quantity());
            accountStockRepository.save(entity);
        }

    }

    public List<AccountStockDTO> getAccountStocks(String accountId) {
        return accountStockRepository
                .findByAccountId(UUID.fromString(accountId))
                .stream()
                .map(item -> new AccountStockDTO(
                        item.getStock().getCode(),
                        item.getQuantity(),
                        getTotal(
                                item.getStock().getCode(),
                                item.getQuantity())))
                .toList();
    }

    private double getTotal(String code, Integer quantity) {
        var response = brapiClient.getQuote(token, code);
        var price_code = response.results().getFirst().regularMarketPrice();
        return price_code * quantity;
    }

}
