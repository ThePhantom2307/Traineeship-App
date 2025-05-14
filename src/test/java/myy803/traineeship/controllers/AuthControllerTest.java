package myy803.traineeship.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import myy803.traineeship.model.User;
import myy803.traineeship.services.UserService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock  private UserService userService;
    @InjectMocks private AuthController controller;

    @Test
    void login_returnsLoginView() {
        assertEquals("auth/login", controller.login());
    }

    @Test
    void register_populatesEmptyUserAndReturnsRegisterView() {
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.register(model);

        assertEquals("auth/register", view);
        assertTrue(model.containsAttribute("user"));
        assertNotNull(model.getAttribute("user"));
    }

    @Test
    void save_whenUserExists_redirectsWithError() {
        User user = new User();
        ConcurrentModel model = new ConcurrentModel();
        when(userService.isUserPresent(user)).thenReturn(true);

        String view = controller.save(user, model);

        assertEquals("redirect:/register?error=true", view);
        verify(userService, never()).saveUser(any());
        assertTrue(model.containsAttribute("errorMessage"));
    }

    @Test
    void save_whenNewUser_savesAndReturnsLoginView() {
        User user = new User();
        ConcurrentModel model = new ConcurrentModel();
        when(userService.isUserPresent(user)).thenReturn(false);

        String view = controller.save(user, model);

        assertEquals("auth/login", view);
        verify(userService).saveUser(user);
        assertTrue(model.containsAttribute("successMessage"));
    }
}
