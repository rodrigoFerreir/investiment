package com.project.investment.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.investment.controllers.dto.CreateAccountDTO;
import com.project.investment.controllers.dto.CreateUserDTO;
import com.project.investment.controllers.dto.ResponseAccountDTO;
import com.project.investment.entities.Account;
import com.project.investment.entities.BillingAddress;
import com.project.investment.entities.User;
import com.project.investment.repositories.AccountRepository;
import com.project.investment.repositories.BillingAddressRepository;
import com.project.investment.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
            BillingAddressRepository billingAddressRepository,
            AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.accountRepository = accountRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {
        // DTO -> ENTITY

        var user = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                Instant.now());

        var userSaved = userRepository.save(user);
        return userSaved.getId();
    };

    public Optional<User> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String userId) {
        var id = UUID.fromString(userId);

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // verifica o se o usuario existe
                                                                                       // se nao gera um erro
        System.out.println("User id :: " + user.getId().toString());
        // DTO -> Entity

        var account = new Account(
                UUID.randomUUID(),
                createAccountDTO.description(),
                user,
                null,
                new ArrayList<>());

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getId(),
                accountCreated,
                createAccountDTO.street(),
                createAccountDTO.number(),
                createAccountDTO.city());

        billingAddressRepository.save(billingAddress);
    }

    public List<ResponseAccountDTO> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // verifica o se o usuario existe
                                                                                       // se nao gera um erro

        return user.getAccounts()
                .stream()
                .map(account -> new ResponseAccountDTO(account.getId().toString(), account.getDescription())).toList();
    }
}