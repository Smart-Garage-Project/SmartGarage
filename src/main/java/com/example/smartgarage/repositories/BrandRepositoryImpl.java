package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Brand;
import com.example.smartgarage.repositories.contracts.BrandRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandRepositoryImpl implements BrandRepository {
    private final SessionFactory sessionFactory;

    public BrandRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Brand getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Brand> query = session.createQuery("from Brand where name = :name", Brand.class);
            query.setParameter("name", name);
            List<Brand> brands = query.list();
            if (brands.isEmpty()) {
                throw new EntityNotFoundException("Brand", "name", name);
            }
            return brands.get(0);
        }
    }

    @Override
    public Brand create(Brand brand) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query<Brand> query = session.createQuery("from Brand where name = :name", Brand.class);
            query.setParameter("name", brand.getName());
            List<Brand> existingBrands = query.list();

            if (!existingBrands.isEmpty()) {
                throw new EntityDuplicateException("A brand with the same name already exists.");
            }

            session.persist(brand);
            session.getTransaction().commit();
        }
        return brand;
    }
}
