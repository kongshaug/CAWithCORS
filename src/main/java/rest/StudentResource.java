package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Student;
import utils.EMF_Creator;
import facades.StudentFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * REST Web Service
 *
 * @author sofieamalielandt
 */

@Path("student")
public class StudentResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final StudentFacade FACADE =  StudentFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllStudents() {
        
        List<Student> students = FACADE.getAllStudents();
        return GSON.toJson(students);
    }

}
