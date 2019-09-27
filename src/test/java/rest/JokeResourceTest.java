package rest;

import entities.Joke;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 * REST Web Service
 *
 * @author aamandajuhl
 */

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class JokeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
    private static final String TEST_DB = "jdbc:mysql://localhost:3307/startcode_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Joke j1;
    private Joke j2;
    private Joke j3;
    private Joke j4;

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

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/joke").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testGetAllJokes() throws Exception {
        given()
                .contentType("application/json")
                .get("/joke/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("theJoke", hasItems("How do you make holy water? ... you boil the hell out of it", 
                        "Yo mama is so fat, that she blocks the wifi signal",
                        "Yo mama is so ugly, when the devil saw her, he started praying",
                        "Did you hear about the resturant on the moon? great food, no atmosphere"),
                        "type", hasItems("Religious", "Yo mama", "Yo mama", "Dad Joke"),
                        "rating", hasItems(1, 2, 3, 2));

    }
    
    //This test assumes the database contains two rows
    @Test
    public void testGetJokeById() throws Exception {
        given()
                .contentType("application/json")
                .get("/joke/{id}", j1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("theJoke", equalTo("How do you make holy water? ... you boil the hell out of it"), "type", equalTo("Religious"), "rating", equalTo(1));

    }
    
    //This test assumes the database contains two rows
    @Test
    public void testGetRandomJoke() throws Exception {
        given()
                .contentType("application/json")
                .get("/joke/random").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

}
