/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.CarDTO;
import entities.Car;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author sofieamalielandt
 */

public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    private CarFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CarFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CarDTO> getAllCars() {
        TypedQuery<Car> query = getEntityManager().createQuery("SELECT c FROM Car c", Car.class);
        List<Car> cars = query.getResultList();
        List<CarDTO> carDTOs = new ArrayList();

        for (Car car : cars) {

            carDTOs.add(new CarDTO(car));
        }

        return carDTOs;
    }

    public CarDTO getCarById(long id) {

        TypedQuery<Car> query = getEntityManager().createQuery("SELECT c FROM Car c where c.id = :id", Car.class);
        Car car = query.setParameter("id", id).getSingleResult();

        return new CarDTO(car);
    }

    public List<CarDTO> getCarsByMake(String make) {
        TypedQuery<Car> query = getEntityManager().createQuery("SELECT c FROM Car c where c.make = :make", Car.class);
        List<Car> cars = query.setParameter("make", make).getResultList();;
        List<CarDTO> carDTOs = new ArrayList();

        for (Car car : cars) {

            carDTOs.add(new CarDTO(car));
        }

        return carDTOs;
    }

}
