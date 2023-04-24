package com.example.bewisepark.Model.types;

import java.util.List;

public class Item {
    public static List<Item> itemList;
    private int violationId, carId;
    private String violation_description, make, model, plate_number;
    private boolean expanded;
    private boolean expendedEdit;

    public Item(int violationId, int carId, String violation_description, String make, String model, String plate_number) {
        this.violationId = violationId;
        this.violation_description = violation_description;
        this.make = make;
        this.model = model;
        this.plate_number = plate_number;
        this.carId = carId;
    }

    public int getCarId() {
        return carId;
    }

    public int getViolationId() {
        return violationId;
    }

    public String getViolation_description() {
        return violation_description;
    }

    public void setViolationId(int violationId) {
        this.violationId = violationId;
    }

    public void setViolation_description(String violation_description) {
        this.violation_description = violation_description;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpendedEdit() {
        return expendedEdit;
    }

    public void setExpendedEdit(boolean expendedEdit) {
        this.expendedEdit = expendedEdit;
    }
}
