package com.example.bewisepark;

// this class is for our Car object and its attributes (i.e. Violations and ID). Can add more if needed
public class Car {
    private String id;
    private String violation;
    private boolean expanded;

    private boolean expendedEdit;

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

    public Car(String id, String violation){
        this.id = id;
        this.violation = violation;
        this.expanded = false;
        this.expendedEdit = false;
    }

    public String getId(){
        return id;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", violation='" + violation + '\'' +
                '}';
    }
}
