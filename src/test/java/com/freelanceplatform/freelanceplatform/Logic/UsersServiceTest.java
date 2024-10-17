package com.freelanceplatform.freelanceplatform.Logic;

import com.freelanceplatform.freelanceplatform.DAL.UserDAL;
import com.freelanceplatform.freelanceplatform.model.dto.UserRegisterDTO;
import com.freelanceplatform.freelanceplatform.model.jpa.Users;
import com.freelanceplatform.freelanceplatform.service.PasswordService;
import com.freelanceplatform.freelanceplatform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UserDAL userDAL;

    @Mock
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        UserService userService = new UserService(userDAL, passwordService);

        Users users1 = Users.builder().id(1L).name("John").email("john@example.com").password("123").build();
        Users users2 = Users.builder().id(2L).name("Jane").email("jane@example.com").password("456").build();
        List<Users> mockUsers = Arrays.asList(users1, users2);

        when(userDAL.findAll()).thenReturn(mockUsers);

        List<Users> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());
    }

    @Test
    void testRegisterUser() {
        UserService userService = new UserService(userDAL, passwordService);

        UserRegisterDTO newUser = UserRegisterDTO.builder().name("Alice").email("alice@example.com").build();

        // Stub het gedrag van de mock repository om de gebruiker op te slaan
        when(userDAL.save(any(Users.class))).thenAnswer(invocation -> {
            Users users = invocation.getArgument(0);
            users.setId(1L); // Stel een ID in voor de nieuwe gebruiker alsof deze is opgeslagen in de database
            return users;
        });
        Users registeredUsers = userService.createUser(newUser);

        // Assert: controleer of de service het juiste resultaat geeft
        assertEquals(1L, registeredUsers.getId());
        assertEquals("Alice", registeredUsers.getName());
        assertEquals("alice@example.com", registeredUsers.getEmail());
    }

    @Test
    void testRegisterUserWithHashedPassword() {
        UserService userService = new UserService(userDAL, passwordService);
        // Arrange: stel een nieuwe gebruiker voor met een raw password
        UserRegisterDTO newUser = UserRegisterDTO.builder().name("Alice").email("alice@example.com").password("rawPassword123").build();

        // Stub het gedrag van de mock repository om de gebruiker op te slaan
        when(userDAL.save(any(Users.class))).thenAnswer(invocation -> {
            Users users = invocation.getArgument(0);
            users.setId(1L); // Stel een ID in voor de nieuwe gebruiker alsof deze is opgeslagen in de database
            return users;
        });

        // Act: registreer de gebruiker via de service
        Users registeredUsers = userService.createUser(newUser);

        // Assert: controleer of de service het juiste resultaat geeft
        assertEquals(1L, registeredUsers.getId());
        assertEquals("Alice", registeredUsers.getName());
        assertEquals("alice@example.com", registeredUsers.getEmail());

        // Controleer dat het wachtwoord is gehasht (vervang met je eigen hash-check-methode)
        assertNotEquals("rawPassword123", registeredUsers.getPassword()); // Controleer dat het wachtwoord niet meer plain text is
    }
}
