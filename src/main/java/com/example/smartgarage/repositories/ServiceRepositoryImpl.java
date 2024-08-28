package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.repositories.contracts.ServiceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ServiceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ServiceModel getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<ServiceModel> query = session.createQuery("from ServiceModel where id = :id", ServiceModel.class);
            query.setParameter("id", id);
            List<ServiceModel> serviceModels = query.list();
            if (serviceModels.isEmpty()) {
                throw new EntityNotFoundException("Service", id);
            }
            return serviceModels.get(0);
        }
    }

    @Override
    public List<ServiceModel> getByCarId(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<ServiceModel> query = session.createQuery("from ServiceModel where car.id = :id", ServiceModel.class);
            query.setParameter("id", id);
            List<ServiceModel> serviceModels = query.list();
            if (serviceModels.isEmpty()) {
                throw new EntityNotFoundException("Services", id);
            }
            return serviceModels;
        }
    }

    @Override
    public ServiceModel create(ServiceModel serviceModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(serviceModel);
            session.getTransaction().commit();
        }
        return serviceModel;
    }

    @Override
    public ServiceModel update(ServiceModel serviceModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(serviceModel);
            session.getTransaction().commit();
        }
        return serviceModel;
    }
}
