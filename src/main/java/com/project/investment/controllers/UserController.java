package com.project.investment.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.investment.controllers.dto.CreateAccountDTO;
import com.project.investment.controllers.dto.CreateUserDTO;
import com.project.investment.controllers.dto.ResponseAccountDTO;
import com.project.investment.entities.User;
import com.project.investment.services.UserService;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO body) {
        var userId = userService.createUser(body);

        return ResponseEntity.created(URI.create("/v1/users/" + userId)).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        var user = userService.getUser(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccountUser(@PathVariable("userId") String userId,
            @RequestBody CreateAccountDTO createAccountDTO) {

        userService.createAccount(userId, createAccountDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<ResponseAccountDTO>> getAccountsUser(@PathVariable("userId") String userId) {

        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }
}
