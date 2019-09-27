/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Car;

/**
 *
 * @author sofieamalielandt
 */
public class CarDTO {

    private Long id;
    private String make;
    private String model;
    private int price;
    private int make_year;

    public CarDTO(Car car) {

        this.id = car.getId();
        this.make = car.getMake();
        this.make_year = car.getMake_year();
        this.model = car.getModel();
        this.price = car.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMake_year() {
        return make_year;
    }

    public void setMake_year(int make_year) {
        this.make_year = make_year;
    }
    
    

}
