package dao;


import entities.Event;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


public class EntityMenager {

    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public EntityMenager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("organizer");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public boolean saveUser(User user) {

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.refresh(user);
        return true;
    }

    public void saveEvent(Event event) {
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        entityManager.refresh(event);
    }

    public List<Event> getAllEvents() {
        Query query = entityManager.createQuery("SELECT e FROM Event e");
        return (List<Event>) query.getResultList();
    }

    public List<User> getAllUsers() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return (List<User>) query.getResultList();
    }

    public void refresh(Object object) {
        entityManager.refresh(object);
    }
}
