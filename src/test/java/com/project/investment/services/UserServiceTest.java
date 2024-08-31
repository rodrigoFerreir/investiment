package com.project.investment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.investment.controllers.dto.CreateUserDTO;
import com.project.investment.entities.User;
import com.project.investment.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    // arrange
    // ACT
    // Assert

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> idArgumentCaptor;

    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateUser() {
            // Arrage
            var input = new CreateUserDTO("test", "email@test.com", "test123@");
            var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            // ACT
            var output = userService.createUser(input);
            var userCaptured = userArgumentCaptor.getValue();

            // Assert
            assertNotNull(output);
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExeptionWhenErrorOccurs() {
            // Arrage
            var input = new CreateUserDTO("test", "email@test.com", "test123@");
            doReturn(new RuntimeException()).when(userRepository).save(any());

            // ACT & Asset
            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }

    }

    @Nested
    class getUser {

        @Test
        @DisplayName("Should success on get user by Id when optional is present")
        void shouldGetUserByIdWenOptionalIsPresent() {
            // Arrage
            var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
            doReturn(Optional.of(user)).when(userRepository).findById(idArgumentCaptor.capture());

            var output = userService.getUser(user.getId().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getId(), idArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should success on get user by Id when optional is not present")
        void shouldGetUserByIdWenOptionalIsNotPresent() {
            // Arrage
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(idArgumentCaptor.capture());

            var output = userService.getUser(userId.toString());

            assertTrue(output.isEmpty());
            assertEquals(userId, idArgumentCaptor.getValue());
        }
    }

    @Nested
    class getAllUsers {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {
            var user = new User(UUID.randomUUID(), "test", "email@test.com", "test123@", Instant.now(), Instant.now());
            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();

            var output = userService.getAllUsers();

            assertNotNull(output);
            assertEquals(userList.size(), output.size());

        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete user with success")
        void shouldDeleteUserWithSuccess() {
            // Arrage
            doReturn(true).when(userRepository).existsById(idArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(idArgumentCaptor.capture());
            var userId = UUID.randomUUID();
            userService.deleteUser(userId.toString());

            var listIds = idArgumentCaptor.getAllValues();
            assertEquals(userId, listIds.get(0));
            assertEquals(userId, listIds.get(1));

            verify(userRepository, times(1)).existsById(listIds.get(0));
            verify(userRepository, times(1)).deleteById(listIds.get(1));
        }

        @Test
        @DisplayName("Should not delete user with success when user not exists")
        void shouldNotDeleteUserWhenUserNotExist() {
            // Arrage
            doReturn(false).when(userRepository).existsById(idArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            userService.deleteUser(userId.toString());

            assertEquals(userId, idArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(idArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());
        }
    }
}
