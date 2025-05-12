
package com.pay_my_buddy.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pay_my_buddy.DTO.RelationDTO;
import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private List<User> mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new ArrayList<>();
        User user01 = new User(1, "aa", "aa@aa.com", "aa");
        User user02 = new User(2, "bb", "bb@b.com", "bb");
        User user03 = new User(3, "cc", "cc@c.com", "cc");
        user01.getConnections().add(user02);
        user02.getConnections().add(user01);
        mockUser.add(user01);
        mockUser.add(user02);
        mockUser.add(user03);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser.get(0)));

        User result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail("aa@aa.com")).thenReturn(Optional.of(mockUser.get(0)));

        User result = userService.getUserByEmail("aa@aa.com");

        assertNotNull(result);
        assertEquals("aa@aa.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail("aa@aa.com");
    }

    @Test
    void testAddUser() {

        when(userRepository.save(mockUser.get(0))).thenReturn(mockUser.get(0));

        User result = userService.addUser(mockUser.get(0));

        assertNotNull(result);
        verify(userRepository, times(1)).save(mockUser.get(0));
    }

    @Test
    void testSaveUser() {

        when(userRepository.save(mockUser.get(0))).thenReturn(mockUser.get(0));

        User result = userService.saveUser(mockUser.get(0));

        assertNotNull(result);
        verify(userRepository, times(1)).save(mockUser.get(0));
    }

    @Test
    void testDeleteUserById() {
        when(userRepository.existsById(1)).thenReturn(true);

        boolean result = userService.deleteUserById(1);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUserByIdnOT() {
        when(userRepository.existsById(1)).thenReturn(false);

        boolean result = userService.deleteUserById(1);

        assertFalse(result);
        verify(userRepository, times(0)).deleteById(1);
    }

    @Test
    void testUserExistsByEmail() {
        when(userRepository.existsByEmail("aa@aa.com")).thenReturn(true);

        boolean result = userService.userExistsByEmail("aa@aa.com");

        assertTrue(result);
        assertEquals("aa@aa.com", mockUser.get(0).getEmail());
        verify(userRepository, times(1)).existsByEmail("aa@aa.com");
    }

    @Test
    void testUserExistsByEmailNotExist() {
        when(userRepository.existsByEmail("not exist")).thenReturn(false);

        boolean result = userService.userExistsByEmail("not exist");

        assertFalse(result);
        assertNotEquals("not exist", mockUser.get(0).getEmail());
        verify(userRepository, times(0)).existsByEmail("aa@aa.com");
    }

    @Test
    void testAuthenticateUser() {
        mockUser.get(0).setPassword(userService.hashPassword("aa"));
        when(userRepository.existsByEmail("aa@aa.com")).thenReturn(true);
        when(userRepository.findByEmail("aa@aa.com")).thenReturn(Optional.of(mockUser.get(0)));

        boolean result = userService.authenticateUser("aa@aa.com", "aa");
        assertTrue(result);
    }

    @Test
    void testAuthenticateUserNegatif() {
        mockUser.get(0).setPassword(userService.hashPassword("aa"));
        when(userRepository.existsByEmail("aa@aa.com")).thenReturn(true);
        when(userRepository.findByEmail("aa@aa.com")).thenReturn(Optional.of(mockUser.get(0)));

        boolean result = userService.authenticateUser("aa@aa.com", "Bas password");
        assertFalse(result);
    }

    @Test
    void testGetRelationsUser() {

        RelationDTO relationDTO = new RelationDTO(2, "bb");
        var result = userService.getRelationsUser(mockUser.get(0));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(relationDTO.getId(), result.get(0).getId());
        assertEquals(relationDTO.getName(), result.get(0).getName());
    }

    @Test
    void testHasRelation() {

        boolean result = userService.hasRelation(mockUser.get(0), mockUser.get(1));

        assertTrue(result);
    }

    @Test
    void testHasNotRelation() {

        boolean result = userService.hasRelation(mockUser.get(2), mockUser.get(1));

        assertFalse(result);
    }

    @Test
    void testHashPassword() {
        String password = "password";
        String hashedPassword = userService.hashPassword(password);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

}