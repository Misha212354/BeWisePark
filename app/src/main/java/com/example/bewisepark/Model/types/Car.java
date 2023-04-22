package com.example.bewisepark.Model.types;

public class Car {
    private String make, model, plate_number, color;
    private int carId;

    public Car(String make, String model, String plate_number, String color, int carId) {
        this.make = make;
        this.model = model;
        this.plate_number = plate_number;
        this.color = color;
        this.carId = carId;
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

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
