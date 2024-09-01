package com.example.smartgarage.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceModelDto {
    private int carId;
    private List<Integer> partsIds;
}
