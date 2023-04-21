package com.example.bewisepark.Model.types;

public class Violation {
    private int userId, carId;
    private String violation_description;

    public Violation(int userId, int carId, String violation_description) {
        this.userId = userId;
        this.carId = carId;
        this.violation_description = violation_description;
    }
}
