/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Joke;
import facades.JokeFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author aamandajuhl
 */
@Path("joke")
public class JokeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final JokeFacade FACADE = JokeFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllJokes() {

        List<Joke> jokes = FACADE.getAllJokes();
        return GSON.toJson(jokes);
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeById(@PathParam("id") long id) {
        Joke joke = FACADE.getJokeById(id);
        return GSON.toJson(joke);
    }

    @GET
    @Path("/random")
    @Produces({MediaType.APPLICATION_JSON})
    public String getRandomJoke() {
        Joke joke= FACADE.getRandomJoke();
        return GSON.toJson(joke);
    }

}
