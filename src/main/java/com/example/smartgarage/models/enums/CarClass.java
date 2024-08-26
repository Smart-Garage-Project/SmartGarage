package com.example.smartgarage.models.enums;

public enum CarClass {
    LOW,
    MID,
    HIGH,
    EXOTIC;

    @Override
    public String toString() {
        switch (this) {
            case LOW:
                return "Low";
            case MID:
                return "Mid";
            case HIGH:
                return "High";
            case EXOTIC:
                return "Exotic";
            default:
                throw  new IllegalArgumentException("Invalid car class");
        }
    }
}
