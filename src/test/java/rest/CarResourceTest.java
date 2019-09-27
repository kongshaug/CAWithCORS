package rest;

import entities.Car;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CarResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
    private static final String TEST_DB = "jdbc:mysql://localhost:3307/startcode_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Car c1;
    private Car c2;
    private Car c3;
    

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        httpServer = startServer();

        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        httpServer.shutdownNow();
    }

//     Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
//    TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        c1 = new Car("Ford", "E20",5000,2000,"lars Carman", "Good");
        c2 = new Car("Jeep", "Jeepy", 6000, 2015,"jenny jappy","Fabulous");
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

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/car").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testGetAllCars() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("make", hasItems("Ford", 
                        "Jeep",
                        "Ford"),
                        "model", hasItems("E20", "Jeepy", "N48"),
                        "price", hasItems(5000, 6000, 2000));

    }
    
    //tests the endpoint that gets the car with the given id
    @Test
    public void testGetCarById() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/{id}", c1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("make", equalTo("Ford"), "price", equalTo(5000), "make_year", equalTo(2000));

    }
    // tests that an array is returned
        @Test
    public void testGetCarByMake() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/make/{make}", c1.getMake()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("make_year", hasItems(2005
                        , 2000));

    }


}
