package com.example.bewisepark;

// this class is for our Car object and its attributes (i.e. Violations and ID). Can add more if needed
public class Car {
    private String carId;
    private String violation;
    private String make;
    private String model;
    private String color;
    private String plate_number;
    private boolean expanded;
    private boolean expendedEdit;

    public Car(String carId, String violation, String make, String model, String color, String plate_number) {
        this.carId = carId;
        this.violation = violation;
        this.make = make;
        this.model = model;
        this.color = color;
        this.plate_number = plate_number;
        this.expanded = false;
        this.expendedEdit = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isExpendedEdit() {
        return expendedEdit;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public void setExpendedEdit(boolean expendedEdit) { this.expendedEdit = expendedEdit; }

    public String getId(){
        return carId;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + carId + '\'' +
                ", violation='" + violation + '\'' +
                '}';
    }
}
