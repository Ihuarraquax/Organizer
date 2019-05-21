package server;


import dao.EventDao;
import dao.UserDao;
import entities.Event;
import entities.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;


public class OrganizerService {

    UserDao userDao;
    EventDao eventDao;

    public OrganizerService(EntityManagerFactory entityManagerFactory) {
        userDao = new UserDao(entityManagerFactory);
        eventDao = new EventDao(entityManagerFactory);
    }

    public boolean registerUser(User user) {

        if (isLoginValid(user.getLogin())) {
            userDao.save(user);
            return true;
        }
        return false;
    }


    public boolean saveEvent(Event event) {
        if (eventIsValid(event)) {
            eventDao.save(event);
            return true;
        }
        return false;
    }

    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }

    public User login(String login, String pass) {
        Optional<User> user = userDao.getAll().stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(pass)).findFirst();
        return user.orElse(null);
    }

    public Event getEvent(long id) {
        return eventDao.get(id).orElse(null);
    }

    public void update(Event event, String[] params) {
        eventDao.update(event,params);
    }

    public void delete(Event event) {
        eventDao.delete(event);
    }

    private boolean eventIsValid(Event event) {
        if (!hasEventConditions(event)) return false;
        long count = eventDao.getAll().stream().filter(e -> e.getName().equals(event.getName())).count();
        return count == 0;
    }

    private boolean hasEventConditions(Event e) {
        if (e.getStartDate().isAfter(e.getEndDate())) return false;
        return true;
        //todo
    }

    private boolean isLoginValid(String login) {
        long count = userDao.getAll().stream().filter(u -> u.getLogin().equals(login)).count();
        return count == 0;
    }
}
