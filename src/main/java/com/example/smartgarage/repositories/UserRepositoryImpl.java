package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepositoryImpl implements UserRepository{
    private SessionFactory sessionFactory;
    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            List<User> list = session.createQuery("from User", User.class).list();
            if (list.isEmpty()) {
                throw new EntityNotFoundException("User", 0);
            }
            return list;
        }
    }
}
