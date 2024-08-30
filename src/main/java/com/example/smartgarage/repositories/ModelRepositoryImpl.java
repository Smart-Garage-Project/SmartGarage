package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Brand;
import com.example.smartgarage.models.Model;
import com.example.smartgarage.repositories.contracts.BrandRepository;
import com.example.smartgarage.repositories.contracts.ModelRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelRepositoryImpl implements ModelRepository {
    private final SessionFactory sessionFactory;

    public ModelRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Model getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> query = session.createQuery("from Model where name = :name", Model.class);
            query.setParameter("name", name);
            List<Model> models = query.list();
            if (models.isEmpty()) {
                throw new EntityNotFoundException("Model", "name", name);
            }
            return models.get(0);
        }
    }

    @Override
    public Model create(Model model) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query<Model> query = session.createQuery("from Model where name = :name", Model.class);
            query.setParameter("name", model.getName());
            List<Model> existingModels = query.list();

            if (!existingModels.isEmpty()) {
                throw new EntityDuplicateException("A model with the same name already exists.");
            }

            session.persist(model);
            session.getTransaction().commit();
        }
        return model;
    }
}
