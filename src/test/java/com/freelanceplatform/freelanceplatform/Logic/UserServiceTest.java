package com.freelanceplatform.freelanceplatform.Logic;

import com.freelanceplatform.freelanceplatform.DAL.Interface.UserDAL;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

    @Mock
    private UserDAL userDAL; // Mock de repository

    @InjectMocks
    private UserLogic userLogic; // Inject de gemockte repository in de service

    @BeforeEach
    void setUp() {
        // Initialiseer de mocks
        MockitoAnnotations.openMocks(this);
    }

   /* @Test
    void testGetAllUsers() {
        // Arrange: stel voor dat we deze gebruikers in de database hebben
        Users user1 = new Users(1L, "John", "john@example.com");
        Users user2 = new Users(2L, "Jane", "jane@example.com");
        List<Users> mockUsers = Arrays.asList(user1, user2);

        // Stub het gedrag van de mock repository
        when(userDAL.findAll()).thenReturn(mockUsers);

        // Act: roep de service aan
        List<Users> result = userLogic.getAllUsers();

        // Assert: controleer of de service het verwachte resultaat teruggeeft
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getUsername());
        assertEquals("Jane", result.get(1).getUsername());
    }*/
}
