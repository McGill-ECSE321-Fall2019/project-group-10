package ca.mcgill.ecse321.project.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(MockitoJUnitRunner.class)
public class RoleBackendTest {

    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private StudentRepository studentRepository;


    @InjectMocks
    private TutoringAppService service;

    private String STUDENT_USERNAME = "StudentUsername";
    private String STUDENT_PASSWORD = "StudentPassword";
    private Student STUDENT = null;

    private String TUTOR_USERNAME = "StudentUsername";
    private String TUTOR_PASSWORD = "StudentPassword";
    private Tutor TUTOR = null;

    @Before
    public void setMockOutput() {

        // run all default setups for mock outputs
        STUDENT = new Student();
        STUDENT.setPassword(STUDENT_USERNAME);
        STUDENT.setPassword(STUDENT_PASSWORD);
        setMockOutputStudent();

        TUTOR = new Tutor();
        TUTOR.setPassword(TUTOR_USERNAME);
        TUTOR.setPassword(TUTOR_PASSWORD);
        setMockOutputTutor();

    }

    //********************************************* MOCK OUTPUTS *********************************************//

    // mock output for student
    private void setMockOutputStudent() {
        when(studentRepository.findStudentByUsername(STUDENT_USERNAME)).thenAnswer((InvocationOnMock invocation) -> {
            return STUDENT;
        });
    }

    // mock output for tutor
    private void setMockOutputTutor() {
        when(tutorRepository.findTutorByUsername(TUTOR_USERNAME)).thenAnswer((InvocationOnMock invocation) -> {
            return TUTOR;
        });
    }



    //************************************************* TESTS *************************************************//

    // Check that getStudent fails with null username
    @Test
    public void getStudentNullUsername() {
        String error = null;

        try {
            service.getStudent(null);
        } catch(IllegalArgumentException e)
        {
            error = e.getMessage();
        }

        // check that error message is correct
        assertEquals(error, ErrorStrings.Invalid_Student_Username);
    }

    // Check that getStudent fails with no username
    @Test
    public void getStudentNoUsername() {
        String error = null;

        try {
            service.getStudent("");
        } catch(IllegalArgumentException e)
        {
            error = e.getMessage();
        }

        // check that error message is correct
        assertEquals(error, ErrorStrings.Invalid_Student_Username);
    }

    // Check that getStudent fails with correct username
    @Test
    public void getStudentCorrectUsername() {
        Student student = null;

        try {
            student = service.getStudent(STUDENT_USERNAME);
        } catch(IllegalArgumentException e)
        {
            fail();
        }

        // check that it returns the correct student
        assertEquals(STUDENT, student);
    }

    // Check that getTutor fails with null username
    @Test
    public void getTutorNullUsername() {
        String error = null;

        try {
            service.getTutor(null);
        } catch(IllegalArgumentException e)
        {
            error = e.getMessage();
        }

        // check that error message is correct
        assertEquals(error, ErrorStrings.Invalid_Tutor_Username);
    }

    // Check that getTutor fails with no username
    @Test
    public void getTutorNoUsername() {
        String error = null;

        try {
            service.getTutor("");
        } catch(IllegalArgumentException e)
        {
            error = e.getMessage();
        }

        // check that error message is correct
        assertEquals(error, ErrorStrings.Invalid_Tutor_Username);
    }

    // Check that getTutor works with correct username
    @Test
    public void getTutorCorrectUsername() {
        Tutor tutor = null;

        try {
            tutor = service.getTutor(TUTOR_USERNAME);
        } catch(IllegalArgumentException e)
        {
            fail();
        }

        // check that it returns the correct tutor
        assertEquals(TUTOR  , tutor);
    }
}
