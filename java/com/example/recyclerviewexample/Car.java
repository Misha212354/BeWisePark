package com.example.recyclerviewexample;

public class Car {
    private String id;
    private String violation;
    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Car(String id, String violation){
        this.id = id;
        this.violation = violation;
        this.expanded = false;
    }

    public String getId(){
        return id;
    }

    public String getViolation() {
        return violation;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", violation='" + violation + '\'' +
                '}';
    }
}
