package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.repositories.contracts.PartRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PartRepositoryImpl implements PartRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PartRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Part getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Part> query = session.createQuery("from Part where id = :id", Part.class);
            query.setParameter("id", id);
            List<Part> parts = query.list();
            if (parts.isEmpty()) {
                throw new EntityNotFoundException("Part", id);
            }
            return parts.get(0);
        }
    }

    @Override
    public List<Part> getAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Part> list = session.createQuery("from Part", Part.class).list();
            if (list.isEmpty()) {
                throw new EntityNotFoundException("Part", 0);
            }
            return list;
        }
    }

    public List<Part> getAll(List<Part> excludedParts) {
        List<Integer> excludedIds = excludedParts.stream()
                .map(Part::getId)
                .collect(Collectors.toList());
        try (Session session = sessionFactory.openSession()) {
            Query<Part> query = session.createQuery("from Part where id not in :ids", Part.class);
            query.setParameterList("ids", excludedIds);
            List<Part> list = query.list();
            if (list.isEmpty()) {
                throw new EntityNotFoundException("Part", 0);
            }
            return list;
        }
    }

    @Override
    public List<Part> getListOfParts(List<Integer> partsIds) {
        try(Session session = sessionFactory.openSession()) {
            Query<Part> query = session.createQuery("from Part where id in :ids", Part.class);
            query.setParameterList("ids", partsIds);
            return query.list();
        }
    }
}
