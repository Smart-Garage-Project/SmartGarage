package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.CarClass;
import com.example.smartgarage.repositories.contracts.CarClassRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarClassRepositoryImpl implements CarClassRepository {
    private final SessionFactory sessionFactory;
    public CarClassRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public CarClass getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<CarClass> query = session.createQuery("from CarClass where name = :name", CarClass.class);
            query.setParameter("name", name);
            List<CarClass> carClasses = query.list();
            if (carClasses.isEmpty()) {
                throw new EntityNotFoundException("CarClass", "name", name);
            }
            return carClasses.get(0);
        }
    }
}
