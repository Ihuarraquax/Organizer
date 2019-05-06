package dao;


import entities.Event;
import entities.User;

import javax.persistence.*;
import java.util.List;


public class EntityMenager {

    private EntityManager entityManager;

    public EntityMenager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("organizer");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public boolean registerUser(User user) {

        if (checkIfLoginValid(user.getLogin())) {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            entityManager.refresh(user);
            return true;
        } else return false;
    }

    private boolean checkIfLoginValid(String login) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.login=:login", User.class);
        query.setParameter("login", login);
        return (query.getResultList().size() == 0);
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
        Query query = entityManager.createQuery("SELECT u FROM User u");
        return (List<User>) query.getResultList();
    }

    public void refresh(Object object) {
        entityManager.refresh(object);
    }

    public User login(String login, String pass) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.login=:login AND u.password=:password", User.class);
        query.setParameter("login", login);
        query.setParameter("password", pass);
        return query.getSingleResult();
    }
}
