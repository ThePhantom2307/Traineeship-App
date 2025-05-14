package myy803.traineeship.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import myy803.traineeship.model.*;
import myy803.traineeship.services.*;

@ExtendWith(MockitoExtension.class)
class TraineeshipCommitteeControllerTest {

    @Mock
    private UserService userService;
    
    @Mock
    private ProfessorService professorService;
    
    @Mock
    private StudentService studentService;
    
    @Mock
    private TraineeshipPositionService positionService;
    
    @InjectMocks
    private TraineeshipCommitteeController controller;

    @Test
    void dashboard_viewName() {
        assertEquals("trainee_committee/dashboard", controller.getStudentMainMenu());
    }

    @Test
    void studentsApplications_listsPendingStudents() {
        when(userService.authenticateAndGetUser()).thenReturn(new User());
        List<Student> pending = List.of(new Student());
        when(studentService.getAllPendingStudents()).thenReturn(pending);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.displayPendingStudentsApplications(model);

        assertEquals("trainee_committee/students_applications", view);
        assertSame(pending, model.getAttribute("studentsApplications"));
    }

    @Test
    void rejectStudent_callsServiceAndRedirects() {
        Student s = new Student();
        when(studentService.getStudent("stud1")).thenReturn(s);

        String view = controller.rejectStudent("stud1");

        verify(studentService).rejectStudent(s);
        assertEquals("redirect:/trainee_committee/students_applications", view);
    }

    @Test
    void acceptStudent_addsFlashAttrsAndRedirects() {
        Student s = new Student();
        when(studentService.getStudent("stud1")).thenReturn(s);
        RedirectAttributesModelMap attrs = new RedirectAttributesModelMap();

        String view = controller.acceptStudent("stud1", attrs);

        assertEquals("redirect:/trainee_committee/find_position", view);
        assertSame(s, attrs.getFlashAttributes().get("student"));
    }
}
