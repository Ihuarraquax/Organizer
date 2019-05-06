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


    public boolean saveEvent(Event event) {
        if (eventIsValid(event)) {
            entityManager.getTransaction().begin();
            System.out.println(event);
            entityManager.persist(event);
            entityManager.getTransaction().commit();
            entityManager.refresh(event);
            return true;
        }
        return false;
    }

    private boolean eventIsValid(Event event) {
        List<Event> events = getAllEvents();
        if(events.stream().filter(e -> e.getName().equals(event.getName()) ).count()>0) return false;
        return true;
    }

    public List<Event> getAllEvents() {
        TypedQuery<Event> query = entityManager.createQuery("SELECT e FROM Event e", Event.class);
        return query.getResultList();
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
        List<User> resultList = query.getResultList();
        if(resultList.size()==1) return resultList.get(0);
        return null;
    }
}
