package ru.alex0d.javaspring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.alex0d.javaspring.models.User;
import ru.alex0d.javaspring.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testLoadUserByUsername() {
        String username = "testuser";
        User user = new User(1L, username, "password");
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(userDetails.getUsername(), username);
        assertEquals(userDetails.getPassword(), user.getPassword());
    }

    @Test
    public void testRegisterUser() {
        String username = "testuser";
        String password = "password";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        User savedUser = new User(1L, username, passwordEncoder.encode(password));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        boolean result = userService.registerUser(username, password);

        assertTrue(result);
        Mockito.verify(userRepository).findByUsername(username);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        String username = "testuser";
        String password = "password";
        User existingUser = new User(1L, username, password);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        boolean result = userService.registerUser(username, password);

        assertFalse(result);
        Mockito.verify(userRepository).findByUsername(username);
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }
}