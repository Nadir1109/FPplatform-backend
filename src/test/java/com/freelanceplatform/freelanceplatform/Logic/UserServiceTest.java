package com.freelanceplatform.freelanceplatform.Logic;

import com.freelanceplatform.freelanceplatform.DAL.UserDAL;
import com.freelanceplatform.freelanceplatform.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAL userDAL;

    @InjectMocks
    private UserLogic userLogic;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {

        Users user1 = new Users(1L, "John", "john@example.com");
        Users user2 = new Users(2L, "Jane","john@mail.com");
        List<Users> mockUsers = Arrays.asList(user1, user2);


        when(userDAL.findAll()).thenReturn(mockUsers);


        List<Users> result = userLogic.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());
    }

    void testRegisterUser() {
        Users newUser = new Users(null, "Alice", "alice@example.com");

        // Stub het gedrag van de mock repository om de gebruiker op te slaan
        when(userDAL.save(any(Users.class))).thenAnswer(invocation -> {
            Users user = invocation.getArgument(0);
            user.setId(1L); // Stel een ID in voor de nieuwe gebruiker alsof deze is opgeslagen in de database
            return user;
        });
        Users registeredUser = userLogic.createUser(newUser);

        // Assert: controleer of de service het juiste resultaat geeft
        assertEquals(1L, registeredUser.getId());
        assertEquals("Alice", registeredUser.getName());
        assertEquals("alice@example.com", registeredUser.getEmail());
}
    @Test
    void testRegisterUserWithHashedPassword() {
        // Arrange: stel een nieuwe gebruiker voor met een raw password
        Users newUser = new Users(null, "Alice", "alice@example.com");
        newUser.setPassword("rawPassword123");

        // Stub het gedrag van de mock repository om de gebruiker op te slaan
        when(userDAL.save(any(Users.class))).thenAnswer(invocation -> {
            Users user = invocation.getArgument(0);
            user.setId(1L); // Stel een ID in voor de nieuwe gebruiker alsof deze is opgeslagen in de database
            return user;
        });

        // Act: registreer de gebruiker via de service
        Users registeredUser = userLogic.createUser(newUser);

        // Assert: controleer of de service het juiste resultaat geeft
        assertEquals(1L, registeredUser.getId());
        assertEquals("Alice", registeredUser.getName());
        assertEquals("alice@example.com", registeredUser.getEmail());

        // Controleer dat het wachtwoord is gehasht (vervang met je eigen hash-check-methode)
        assertNotEquals("rawPassword123", registeredUser.getPassword()); // Controleer dat het wachtwoord niet meer plain text is
    }
}
