package rest;

import entities.Student;
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
 * @author sofieamalielandt
 */

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class StudentResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
    private static final String TEST_DB = "jdbc:mysql://localhost:3307/startcode_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Student s1;
    private Student s2;
    private Student s3;

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
        s1 = new Student("Amanda Juhl Hansen", "Yellow", "Female", 24);
        s2 = new Student("Benjamin Kongshaug", "Red", "Male", 24);
        s3 = new Student("Sofie Amalie Landt", "Red", "Female", 24);

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

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/student").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testGetAllStudents() throws Exception {
        given()
                .contentType("application/json")
                .get("/student/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems("Sofie Amalie Landt", "Benjamin Kongshaug", "Amanda Juhl Hansen"),
                        "age", hasItems(24, 24, 24),
                        "color", hasItems("Red", "Red", "Yellow"),
                        "gender", hasItems("Female", "Female", "Male"));

    }

}
