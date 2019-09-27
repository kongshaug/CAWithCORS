package facades;

import entities.Student;
import java.util.List;
import javax.persistence.EntityManager;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author aamandajuhl
 */

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class StudentFacadeTest {

    private static EntityManagerFactory emf;
    private static StudentFacade facade;
    private Student s1;
    private Student s2;
    private Student s3;

    public StudentFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = StudentFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Student.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        s1 = new Student("Amanda Juhl Hansen", "Yellow", "Female", 24);
        s2 = new Student("Benjamin Kongshaug", "Red", "Male", 24);
        s3 = new Student("Sofie Amalie Landt", "Red", "Femaler", 24);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Student.deleteAllRows").executeUpdate();
            em.persist(s1);
            em.persist(s2);
            em.persist(s3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    /**
     * Test of addStudent method, of class StudentFacade.
     */
    @Test
    public void testAddStudent() {

        int studentsbefore = facade.getAllStudents().size();
        Student student = new Student("Laura Saxtrup Nielsen", "Green", "Female", 23);
        facade.addStudent(student);
        int studentsafter = facade.getAllStudents().size();

        assertTrue(studentsbefore < studentsafter);
    }

    /**
     * Test of getAllStudents method, of class StudentFacade.
     */
    @Test
    public void testGetAllStudents() {
        List<Student> students = facade.getAllStudents();
        assertFalse(students.isEmpty());
        assertEquals(3, students.size());
    }
}
