package myy803.traineeship.services;

import myy803.traineeship.dao.StudentDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        student.setUsername("test_username");
        studentService.saveStudent(student);
        verify(studentDAO, times(1)).save(student);
    }

    @Test
    public void testIsStudentExists_True() {
        when(studentDAO.findByUsername("test_username")).thenReturn(Optional.of(new Student()));

        Assertions.assertTrue(studentService.doesStudentExist("test_username"));
        verify(studentDAO).findByUsername("test_username");
    }

    @Test
    public void testIsStudentExists_False() {
        when(studentDAO.findByUsername("test_username")).thenReturn(Optional.empty());

        Assertions.assertFalse(studentService.doesStudentExist("test_username"));
        verify(studentDAO).findByUsername("test_username");
    }

    @Test
    public void testGetStudent_Exists() {
        Student student = new Student();
        student.setUsername("test_username");
        student.setFullname("test_fullname");

        when(studentDAO.findByUsername("test_username")).thenReturn(Optional.of(student));

        Student result = studentService.getStudent("test_username");

        Assertions.assertEquals("test_username", result.getUsername());
        Assertions.assertEquals("test_fullname", result.getFullname());
        verify(studentDAO).findByUsername("test_username");
    }

    @Test
    public void testGetStudent_NotExists() {
        when(studentDAO.findByUsername("unknown")).thenReturn(Optional.empty());

        Student result = studentService.getStudent("unknown");

        Assertions.assertEquals("unknown", result.getUsername());
        Assertions.assertEquals("", result.getFullname());
        Assertions.assertEquals("", result.getInterests());
        verify(studentDAO).findByUsername("unknown");
    }
    
    @Test
    public void testApplyForTraineeship_StudentNotExist() {
        Student student = new Student();
        student.setUsername("unknown");
        when(studentDAO.findByUsername("unknown")).thenReturn(Optional.empty());

        String view = studentService.applyForTraineeship(student);

        Assertions.assertEquals("redirect:/student/traineeship_application?error=true", view);
        verify(studentDAO, never()).save(any(Student.class));
    }

    @Test
    public void testApplyForTraineeship_TraineeshipFinished() {
        Student student = new Student();
        student.setUsername("finished");
        TraineeshipPosition position = mock(TraineeshipPosition.class);
        when(position.getPassFailGrade()).thenReturn(true);
        student.setTraineeshipPosition(position);
        when(studentDAO.findByUsername("finished")).thenReturn(Optional.of(student));

        String view = studentService.applyForTraineeship(student);

        Assertions.assertEquals("redirect:/student/traineeship_application?traineeship_finished=true", view);
        verify(studentDAO, never()).save(any(Student.class));
    }

    @Test
    public void testApplyForTraineeship_AlreadyAssigned() {
        Student student = new Student();
        student.setUsername("assigned");
        TraineeshipPosition position = mock(TraineeshipPosition.class);
        when(position.getPassFailGrade()).thenReturn(null);
        student.setTraineeshipPosition(position);
        when(studentDAO.findByUsername("assigned")).thenReturn(Optional.of(student));

        String view = studentService.applyForTraineeship(student);

        Assertions.assertEquals("redirect:/student/traineeship_application?already_assigned=true", view);
        verify(studentDAO, never()).save(any(Student.class));
    }

    @Test
    public void testApplyForTraineeship_AlreadyLooking() {
        Student student = new Student();
        student.setUsername("looking");
        student.setLookingForTraineeship(true);
        when(studentDAO.findByUsername("looking")).thenReturn(Optional.of(student));

        String view = studentService.applyForTraineeship(student);

        Assertions.assertEquals("redirect:/student/traineeship_application?note=true", view);
        verify(studentDAO, never()).save(any(Student.class));
    }

    @Test
    public void testApplyForTraineeship_Success() {
        Student student = new Student();
        student.setUsername("newapplicant");
        student.setLookingForTraineeship(false);
        student.setTraineeshipPosition(null);
        when(studentDAO.findByUsername("newapplicant")).thenReturn(Optional.of(student));

        String view = studentService.applyForTraineeship(student);

        Assertions.assertEquals("redirect:/student/traineeship_application?success=true", view);
        Assertions.assertTrue(student.getLookingForTraineeship());
        verify(studentDAO, times(1)).save(student);
    }
    
    @Test
    public void testRejectStudent() {
        Student student = new Student();
        student.setUsername("applicant");
        student.setLookingForTraineeship(true);

        studentService.rejectStudent(student);

        Assertions.assertFalse(student.getLookingForTraineeship());
        verify(studentDAO, times(1)).save(student);
    }

    @Test
    public void testGetAllPendingStudents() {
        Student s1 = new Student();
        Student s2 = new Student();
        List<Student> pending = Arrays.asList(s1, s2);
        when(studentDAO.findByLookingForTraineeship(true)).thenReturn(pending);

        List<Student> result = studentService.getAllPendingStudents();

        Assertions.assertEquals(pending, result);
        verify(studentDAO, times(1)).findByLookingForTraineeship(true);
    }
}
