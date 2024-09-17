package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.repositories.contracts.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartServiceImplTests {

    @Mock
    private PartRepository partRepository;

    @InjectMocks
    private PartServiceImpl partService;

    private Part part;

    @BeforeEach
    void setUp() {
        part = new Part();
        part.setId(1);
        part.setName("Brake Pad");
    }

    @Test
    void testGetAllParts() {
        when(partRepository.findAll()).thenReturn(List.of(part));

        List<Part> parts = partService.getAllParts();

        assertNotNull(parts);
        assertEquals(1, parts.size());
        assertEquals("Brake Pad", parts.get(0).getName());
        verify(partRepository, times(1)).findAll();
    }

    @Test
    void testGetParts() {
        Page<Part> page = mock(Page.class);
        when(partRepository.findAllPaged(any(Pageable.class))).thenReturn(page);

        Page<Part> partsPage = partService.getParts(Pageable.unpaged());

        assertNotNull(partsPage);
        verify(partRepository, times(1)).findAllPaged(any(Pageable.class));
    }

    @Test
    void testGetExcludedParts() {
        List<Part> parts = List.of(part);
        when(partRepository.findAllExcluded(anyList())).thenReturn(parts);

        List<Part> partsPage = partService.getExcludedParts(List.of(1));

        assertNotNull(partsPage);
        assertEquals(1, partsPage.size());
        assertEquals("Brake Pad", partsPage.get(0).getName());
        verify(partRepository, times(1)).findAllExcluded(anyList());
    }

    @Test
    void testGetById() {
        when(partRepository.findById(1)).thenReturn(Optional.of(part));

        Part foundPart = partService.getById(1);

        assertNotNull(foundPart);
        assertEquals("Brake Pad", foundPart.getName());
        verify(partRepository, times(1)).findById(1);
    }

    @Test
    void testGetById_NotFound() {
        when(partRepository.findById(1)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> partService.getById(1));

        assertEquals("Part with id 1 not found.", exception.getMessage());
        verify(partRepository, times(1)).findById(1);
    }
}