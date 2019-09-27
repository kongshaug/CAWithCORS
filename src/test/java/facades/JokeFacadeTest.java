package facades;

import entities.Joke;
import utils.EMF_Creator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author benja
 */
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class JokeFacadeTest {

    private static EntityManagerFactory emf;
    private static JokeFacade facade;

    private Joke j1;
    private Joke j2;
    private Joke j3;
    private Joke j4;

    public JokeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = JokeFacade.getFacade(emf);

    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
//     Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
//    TODO -- Make sure to change the script below to use YOUR OWN entity class

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        j1 = new Joke("How do you make holy water? ... you boil the hell out of it", "Religious", 1);
        j2 = new Joke("Yo mama is so fat, that she blocks the wifi signal", "Yo mama", 2);
        j3 = new Joke("Yo mama is so ugly, when the devil saw her, he started praying", "Yo mama", 3);
        j4 = new Joke("Did you hear about the resturant on the moon? great food, no atmosphere", "Dad Joke", 2);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.persist(j1);
            em.persist(j2);
            em.persist(j3);
            em.persist(j4);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    /**
     * Test of getAllJokes method, of class JokeFacade.
     */
    @Test
    public void testGetAllJokes() {

        List<Joke> jokes = facade.getAllJokes();
        int numJokes = jokes.size();

        assertEquals(numJokes, 4);
    }

    @Test
    public void testGetRandomJoke() {

        Joke joke = facade.getRandomJoke();
        joke.setRating(3);
        assertEquals(joke.getRating(), 3);

    }

    @Test
    public void testGetJokebyId() {

        Joke joke = facade.getJokeById(j1.getId());
        assertEquals(joke.getRating(), 1);
        assertEquals(joke.getType(), "Religious");
    }

}
