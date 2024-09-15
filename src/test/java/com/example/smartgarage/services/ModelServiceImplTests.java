package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Model;
import com.example.smartgarage.repositories.contracts.ModelRepository;
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
public class ModelServiceImplTests {

    @Mock
    private ModelRepository modelRepository;

    @InjectMocks
    private ModelServiceImpl modelService;

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
        model.setId(1);
        model.setName("TestModel");
    }

    @Test
    void testGetByName() {
        when(modelRepository.findByName("TestModel")).thenReturn(Optional.of(model));

        Model foundModel = modelService.getByName("TestModel");

        assertNotNull(foundModel);
        assertEquals("TestModel", foundModel.getName());
        verify(modelRepository, times(1)).findByName("TestModel");
    }

    @Test
    void testGetByName_NotFound() {
        when(modelRepository.findByName("TestModel")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> modelService.getByName("TestModel"));

        assertEquals("Model with name TestModel not found.", exception.getMessage());
        verify(modelRepository, times(1)).findByName("TestModel");
    }

    @Test
    void testCreate() {
        when(modelRepository.save(model)).thenReturn(model);

        Model createdModel = modelService.create(model);

        assertNotNull(createdModel);
        assertEquals("TestModel", createdModel.getName());
        verify(modelRepository, times(1)).save(model);
    }
}