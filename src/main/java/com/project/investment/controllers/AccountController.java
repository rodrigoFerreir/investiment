package com.project.investment.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.investment.controllers.dto.AccountStockDTO;
import com.project.investment.entities.Stock;
import com.project.investment.services.AccountService;

@RestController
@RequestMapping("v1/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Stock> associateStock(@PathVariable("accountId") String accountId,
            @RequestBody AccountStockDTO body) {

        accountService.associateStock(accountId, body);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockDTO>> getAccountStocks(@PathVariable("accountId") String accountId) {
        var account = accountService.getAccountStocks(accountId);

        return ResponseEntity.ok(account);
    }

}
