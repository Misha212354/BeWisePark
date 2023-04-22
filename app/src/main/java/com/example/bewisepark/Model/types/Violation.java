package com.example.bewisepark.Model.types;

public class Violation {
    private int userId, carId, violationId;
    private String violation_description;

    public Violation(int userId, int carId, String violation_description) {
        this.userId = userId;
        this.carId = carId;
        this.violation_description = violation_description;
    }

    public Violation(int userId, int carId, int violationId, String violation_description) {
        this.userId = userId;
        this.carId = carId;
        this.violationId = violationId;
        this.violation_description = violation_description;
    }

    public int getCarId() {
        return carId;
    }

    public int getUserId() {
        return userId;
    }

    public int getViolationId() {
        return violationId;
    }

    public String getViolation_description() {
        return violation_description;
    }
}
