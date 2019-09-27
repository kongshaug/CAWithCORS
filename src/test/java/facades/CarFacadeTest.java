package facades;

import dto.CarDTO;
import entities.Car;
import java.util.List;
import javax.persistence.EntityManager;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author sofieamalielandt
 */

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;
    private Car c1;
    private Car c2;
    private Car c3;

    public CarFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = CarFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        c1 = new Car("Ford", "E20",5000, 2000,"lars Carman", "Good");
        c2 = new Car("Jeep", "Jeepy", 6000, 2015,"jenny jappy", "Fablous");
        c3 = new Car("Ford", "N48", 2000, 2005,"ben biky", "Bad");
 
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
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
     * Test of getAllCars method, of class CarFacade.
     */
    @Test
    public void testgetAllCars() {
        List<CarDTO> cars = facade.getAllCars();
        assertFalse(cars.isEmpty());
        assertEquals(3, cars.size());
    }
    
    /**
     * Test of getCarById method, of class CarFacade.
     */
    @Test
    public void testgetCarById() {

        CarDTO car = facade.getCarById(c1.getId());
        assertEquals(car.getMake(), "Ford");
        assertEquals(car.getMake_year(), 2000);
        assertEquals(car.getModel(), "E20");
    }
    
    /**
     * Test of getCarByMake method, of class CarFacade.
     */
    @Test
    public void testgetCarByMake() {

        List<CarDTO> cars = facade.getCarsByMake("Ford");
        assertEquals(cars.size(), 2);
    }
}
