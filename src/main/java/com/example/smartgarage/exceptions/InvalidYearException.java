package com.example.smartgarage.exceptions;

public class InvalidYearException extends RuntimeException {
    public InvalidYearException(int beginYear, int endYear) {
        super(String.format("Year must be between %d and %d", beginYear, endYear));
    }
}
