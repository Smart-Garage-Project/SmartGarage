package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.repositories.contracts.CarRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CarRepositoryImpl implements CarRepository {

    private final SessionFactory sessionFactory;

    public CarRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Car> getUserCars(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("from Car where owner.id = :userId", Car.class);
            query.setParameter("userId", userId);
            List<Car> cars = query.list();
            if (cars.isEmpty()) {
                throw new EntityNotFoundException("Cars", userId);
            }
            return cars;
        }
    }

    @Override
    public Car getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("from Car where id = :carId", Car.class);
            query.setParameter("carId", id);
            List<Car> cars = query.list();
            if (cars.isEmpty()) {
                throw new EntityNotFoundException("Car", id);
            }
            return cars.get(0);
        }
    }

    @Override
    public Car create(Car car) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query<Car> query = session.createQuery("from Car where licensePlate = :licensePlate", Car.class);
            query.setParameter("licensePlate", car.getLicensePlate());
            List<Car> existingCars = query.list();

            if (!existingCars.isEmpty()) {
                throw new EntityDuplicateException("A car with the same license plate already exists.");
            }

            session.persist(car);
            session.getTransaction().commit();
        }
        return car;
    }


    @Override
    public Car update(int id, Car car) {
        getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(car);
            session.getTransaction().commit();
        }
        return getById(id);
    }

    @Override
    public List<Car> getCars() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Car", Car.class).list();
        }
    }
}
