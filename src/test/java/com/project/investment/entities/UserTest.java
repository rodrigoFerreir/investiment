package com.project.investment.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Test
    void testGetCreatedAt() {
        var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
        assertInstanceOf(Instant.class, user.getCreatedAt());
    }

    @Test
    void testGetEmail() {
        var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
        assertInstanceOf(String.class, user.getEmail());
        assertEquals(user.getEmail(), "email@test.com");
    }

    @Test
    void testGetId() {
        var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
        assertInstanceOf(UUID.class, user.getId());
    }

    @Test
    void testGetPassword() {
        var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
        assertInstanceOf(String.class, user.getPassword());
        assertEquals(user.getPassword(), "test123@");
    }

    @Test
    void testGetUpdatedAt() {
        var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
        assertInstanceOf(Instant.class, user.getCreatedAt());
    }

    @Test
    void testGetUsername() {
        var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
        assertInstanceOf(String.class, user.getUsername());
    }

    @Test
    void testSetCreatedAt() {
        var instanceUser = new User();
        var value = Instant.now();
        instanceUser.setCreatedAt(value);
        assertEquals(instanceUser.getCreatedAt(), value);
    }

    @Test
    void testSetEmail() {
        var instanceUser = new User();
        var value = "teste@email.com";
        instanceUser.setEmail(value);
        assertEquals(instanceUser.getEmail(), value);
    }

    @Test
    void testSetId() {
        var instanceUser = new User();
        var value = UUID.randomUUID();
        instanceUser.setId(value);
        assertEquals(instanceUser.getId(), value);
    }

    @Test
    void testSetPassword() {
        var instanceUser = new User();
        var value = "teste@1234";
        instanceUser.setPassword(value);
        assertEquals(instanceUser.getPassword(), value);
    }

    @Test
    void testSetUpdatedAt() {
        var instanceUser = new User();
        var value = Instant.now();
        instanceUser.setUpdatedAt(value);
        assertEquals(instanceUser.getUpdatedAt(), value);
    }

    @Test
    void testSetUsername() {
        var instanceUser = new User();
        var value = "testUsername";
        instanceUser.setUsername(value);
        assertEquals(instanceUser.getUsername(), value);
    }
}
