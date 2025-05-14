package myy803.traineeship.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import myy803.traineeship.model.*;
import myy803.traineeship.services.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock private UserService userService;
    @Mock private StudentService studentService;
    @Mock private TraineeshipPositionService positionService;
    @InjectMocks private StudentController controller;

    private User user;
    private Student student;

    @BeforeEach
    void init() {
        user = new User(); user.setUsername("stud1");
        student = new Student(); student.setUsername("stud1");
    }

    @Test
    void dashboard_viewName() {
        assertEquals("student/dashboard", controller.getStudentMainMenu());
    }

    @Test
    void getProfile_populatesModel() {
        when(userService.authenticateAndGetUser()).thenReturn(user);
        when(studentService.getStudent("stud1")).thenReturn(student);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.getProfile(model);

        assertEquals("student/profile", view);
        assertSame(student, model.getAttribute("profile"));
    }

    @Test
    void saveProfile_persistsAndRedirects() {
        String view = controller.saveStudentProfile(student);

        verify(studentService).saveStudent(student);
        assertEquals("redirect:/student/dashboard", view);
    }

    @Test
    void applyForTraineeship_returnsForm() {
        when(userService.authenticateAndGetUser()).thenReturn(user);
        when(studentService.getStudent("stud1")).thenReturn(student);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.applyForTraineeship(model);

        assertEquals("student/traineeship_application", view);
        assertSame(student, model.getAttribute("application"));
    }
}
