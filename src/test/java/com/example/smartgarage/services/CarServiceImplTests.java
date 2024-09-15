package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.helpers.CarMapper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTests {

    @Mock
    private CarMapper carMapper;

    @Mock
    private CarRepository carRepository;

    @Mock
    private AuthorizationHelper authorizationHelper;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;
    private CarDto carDto;
    private User user;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1);
        car.setLicensePlate("ABC123");

        carDto = new CarDto();
        carDto.setLicensePlate("ABC123");

        user = new User();
        user.setId(1);
    }

    @Test
    void testGetByLicensePlate() {
        when(carRepository.findByLicensePlate("ABC123")).thenReturn(Optional.of(car));

        Car foundCar = carService.getByLicensePlate("ABC123");

        assertNotNull(foundCar);
        assertEquals("ABC123", foundCar.getLicensePlate());
        verify(carRepository, times(1)).findByLicensePlate("ABC123");
    }

    @Test
    void testGetByLicensePlate_NotFound() {
        when(carRepository.findByLicensePlate("ABC123")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> carService.getByLicensePlate("ABC123"));

        assertEquals("Car with License Plate ABC123 not found.", exception.getMessage());
        verify(carRepository, times(1)).findByLicensePlate("ABC123");
    }

    @Test
    void testGetAllCarsFiltered() {
        Page<Car> page = mock(Page.class);
        when(carRepository.findAllFiltered(anyString(), anyString(), anyString(),
                anyString(), any(Pageable.class))).thenReturn(page);

        Page<Car> carsPage = carService.getAllCarsFiltered(user, "brand", "model",
                "ABC123", "IPopov",Pageable.unpaged());

        assertNotNull(carsPage);
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(carRepository, times(1)).findAllFiltered(anyString(), anyString(),
                anyString(), anyString(), any(Pageable.class));
    }

    @Test
    void testGetUserCars() {
        Page<Car> page = mock(Page.class);
        when(carRepository.findUserCars(anyInt(), any(Pageable.class))).thenReturn(page);

        Page<Car> carsPage = carService.getUserCars(1, user, Pageable.unpaged());

        assertNotNull(carsPage);
        verify(authorizationHelper, times(1))
                .checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(user, 1);
        verify(carRepository, times(1)).findUserCars(anyInt(), any(Pageable.class));
    }

    @Test
    void testGetById() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car));

        Car foundCar = carService.getById(1, user);

        assertNotNull(foundCar);
        assertEquals(1, foundCar.getId());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    void testGetById_NotFound() {
        when(carRepository.findById(1)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> carService.getById(1, user));

        assertEquals("Car with id 1 not found.", exception.getMessage());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    void testCreate() {
        when(carRepository.findByLicensePlate("ABC123")).thenReturn(Optional.empty());
        when(carMapper.fromDto(carDto)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);

        Car createdCar = carService.create(carDto, user);

        assertNotNull(createdCar);
        assertEquals("ABC123", createdCar.getLicensePlate());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(carRepository, times(1)).findByLicensePlate("ABC123");
        verify(carMapper, times(1)).fromDto(carDto);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testCreate_Duplicate() {
        when(carRepository.findByLicensePlate("ABC123")).thenReturn(Optional.of(car));

        EntityDuplicateException exception = assertThrows(EntityDuplicateException.class, () -> carService.create(carDto, user));

        assertEquals("Car with license plate ABC123 already exists.", exception.getMessage());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(carRepository, times(1)).findByLicensePlate("ABC123");
    }

    @Test
    void testUpdate() {
        when(carRepository.save(car)).thenReturn(car);

        Car updatedCar = carService.update(1, car, user);

        assertNotNull(updatedCar);
        assertEquals(1, updatedCar.getId());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(carRepository, times(1)).save(car);
    }
}