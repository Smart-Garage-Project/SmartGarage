package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Brand;
import com.example.smartgarage.repositories.contracts.BrandRepository;
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
public class BrandServiceImplTests {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    private Brand brand;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1);
        brand.setName("Toyota");
    }

    @Test
    void testGetByName() {
        when(brandRepository.findByName("Toyota")).thenReturn(Optional.of(brand));

        Brand foundBrand = brandService.getByName("Toyota");

        assertNotNull(foundBrand);
        assertEquals("Toyota", foundBrand.getName());
        verify(brandRepository, times(1)).findByName("Toyota");
    }

    @Test
    void testGetByName_NotFound() {
        when(brandRepository.findByName("Toyota")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> brandService.getByName("Toyota"));

        assertEquals("Brand with name Toyota not found.", exception.getMessage());
        verify(brandRepository, times(1)).findByName("Toyota");
    }

    @Test
    void testCreate() {
        when(brandRepository.save(brand)).thenReturn(brand);

        Brand createdBrand = brandService.create(brand);

        assertNotNull(createdBrand);
        assertEquals("Toyota", createdBrand.getName());
        verify(brandRepository, times(1)).save(brand);
    }
}