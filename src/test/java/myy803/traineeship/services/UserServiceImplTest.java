package myy803.traineeship.services;

import myy803.traineeship.dao.UserDAO;
import myy803.traineeship.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setPassword("rawPassword");

        when(bCryptPasswordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        Assertions.assertEquals("encodedPassword", user.getPassword());
        verify(userDAO).save(user);
    }

    @Test
    public void testIsUserPresent_True() {
        User user = new User();
        user.setUsername("testuser");

        when(userDAO.findByUsername("testuser")).thenReturn(Optional.of(user));

        boolean result = userService.isUserPresent(user);

        Assertions.assertTrue(result);
        verify(userDAO).findByUsername("testuser");
    }

    @Test
    public void testIsUserPresent_False() {
        User user = new User();
        user.setUsername("ghost");

        when(userDAO.findByUsername("ghost")).thenReturn(Optional.empty());

        boolean result = userService.isUserPresent(user);

        Assertions.assertFalse(result);
        verify(userDAO).findByUsername("ghost");
    }

    @Test
    public void testLoadUserByUsername_Exists() {
        User user = new User();
        user.setUsername("john");

        when(userDAO.findByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.loadUserByUsername("john");

        Assertions.assertEquals("john", result.getUsername());
        verify(userDAO).findByUsername("john");
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        when(userDAO.findByUsername("unknown")).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("unknown");
        });

        verify(userDAO).findByUsername("unknown");
    }

    @Test
    public void testAuthenticateAndGetUser() {
        // Setup
        User user = new User();
        user.setUsername("authenticatedUser");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("authenticatedUser");

        SecurityContextHolder.getContext().setAuthentication(mockAuth);
        when(userDAO.findByUsername("authenticatedUser")).thenReturn(Optional.of(user));

        User result = userService.authenticateAndGetUser();

        Assertions.assertEquals("authenticatedUser", result.getUsername());
        verify(userDAO).findByUsername("authenticatedUser");

        SecurityContextHolder.clearContext();
    }
}
