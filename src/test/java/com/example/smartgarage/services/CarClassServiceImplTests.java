package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.CarClass;
import com.example.smartgarage.repositories.contracts.CarClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarClassServiceImplTests {

    @Mock
    private CarClassRepository carClassRepository;

    @InjectMocks
    private CarClassServiceImpl carClassService;

    private CarClass carClass;

    @BeforeEach
    void setUp() {
        carClass = new CarClass();
        carClass.setId(1);
        carClass.setName("Exotic");
    }

    @Test
    void testGetByName() {
        when(carClassRepository.findByName("Exotic")).thenReturn(Optional.of(carClass));

        CarClass foundCarClass = carClassService.getByName("Exotic");

        assertNotNull(foundCarClass);
        assertEquals("Exotic", foundCarClass.getName());
        verify(carClassRepository, times(1)).findByName("Exotic");
    }

    @Test
    void testGetByName_NotFound() {
        when(carClassRepository.findByName("Exotic")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> carClassService.getByName("Exotic"));

        assertEquals("Car class with name Exotic not found.", exception.getMessage());
        verify(carClassRepository, times(1)).findByName("Exotic");
    }
}