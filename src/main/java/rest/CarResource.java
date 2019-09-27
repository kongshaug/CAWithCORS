/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CarDTO;

import facades.CarFacade;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author benja
 */
@Path("car")
public class CarResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final CarFacade FACADE = CarFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CarResource
     */
    public CarResource() {
    }

    /**
     * Retrieves representation of an instance of rest.CarResource
     *
     * @return an instance of java.lang.String
     */
    

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String gottaLoveThemCars() {

      
        return "i Love cars!";
    }
    
    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllCars() {

        List<CarDTO> cars = FACADE.getAllCars();
        return GSON.toJson(cars);
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getcarById(@PathParam("id") long id) {
        CarDTO car = FACADE.getCarById(id);
        return GSON.toJson(car);
    }

    @Path("/make/{make}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMakeCars(@PathParam("make") String make) {

        List<CarDTO> cars = FACADE.getCarsByMake(make);
        return GSON.toJson(cars);
    }

//    @GET
//    @Path("/random")
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getRandomCar() {
//        Car car= FACADE.getRandomCar();
//        return GSON.toJson(car);
//    }
}
