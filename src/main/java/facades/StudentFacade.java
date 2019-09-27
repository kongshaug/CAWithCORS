package facades;

import entities.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author aamandajuhl
 */

public class StudentFacade {

    private static StudentFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private StudentFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static StudentFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Student addStudent(Student student) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } finally {
            em.close();
        }
    }

    public List<Student> getAllStudents() {

        TypedQuery<Student> query = getEntityManager().createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

}
