package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.*;
import com.example.smartgarage.repositories.contracts.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceImplTests {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private AuthorizationHelper authorizationHelper;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    private ServiceModel serviceModel;
    private User user;
    private Part part1;
    private Part part2;
    private Car car;
    private Model carModel;
    private CarClass carClass;

    @BeforeEach
    void setUp() {
        part1 = new Part();
        part1.setId(1);
        part1.setPrice(100.0);

        part2 = new Part();
        part2.setId(2);
        part2.setPrice(200.0);

        carClass = new CarClass();
        carClass.setName("Mid");

        carModel = new Model();
        carModel.setCarClass(carClass);

        car = new Car();
        car.setModel(carModel);

        serviceModel = new ServiceModel();
        serviceModel.setId(1);
        serviceModel.setCar(car);
        serviceModel.setParts(new ArrayList<>());

        user = new User();
        user.setId(1);
    }

    @Test
    void testGetById() {
        when(serviceRepository.findById(1)).thenReturn(Optional.of(serviceModel));

        ServiceModel foundService = serviceService.getById(1);

        assertNotNull(foundService);
        assertEquals(1, foundService.getId());
        verify(serviceRepository, times(1)).findById(1);
    }

    @Test
    void testGetById_NotFound() {
        when(serviceRepository.findById(1)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> serviceService.getById(1));

        assertEquals("Service with id 1 not found.", exception.getMessage());
        verify(serviceRepository, times(1)).findById(1);
    }

    @Test
    void testGetByCarId() {
        Page<ServiceModel> page = mock(Page.class);
        when(serviceRepository.findCarServices(anyInt(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class))).thenReturn(page);

        Page<ServiceModel> servicesPage = serviceService.getByCarId(1, LocalDate.now(), LocalDate.now(), Pageable.unpaged());

        assertNotNull(servicesPage);
        verify(serviceRepository, times(1)).findCarServices(anyInt(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class));
    }

    @Test
    void testCreate() {
        when(serviceRepository.save(serviceModel)).thenReturn(serviceModel);

        ServiceModel createdService = serviceService.create(serviceModel, user);

        assertNotNull(createdService);
        assertEquals(1, createdService.getId());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(serviceRepository, times(1)).save(serviceModel);
    }

    @Test
    void testAddPartsToService() {
        when(serviceRepository.findById(1)).thenReturn(Optional.of(serviceModel));
        when(serviceRepository.save(serviceModel)).thenReturn(serviceModel);

        serviceService.addPartsToService(1, List.of(part1, part2), user);

        assertEquals(2, serviceModel.getParts().size());
        assertEquals(420.0, serviceModel.getTotal()); // 1.4 * (100 + 200)
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(serviceRepository, times(1)).findById(1);
        verify(serviceRepository, times(1)).save(serviceModel);
    }
}