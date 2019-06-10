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
        System.out.println("checking if event is valid");
        if (eventIsValid(event)) {
            System.out.println("event is valid");
            eventDao.save(event);
            return true;
        }
        System.out.println("event is invalid");
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
        if(count!=0) System.out.println("event exist in database");
        return count == 0;
    }

    private boolean hasEventConditions(Event e) {
        System.out.println("checking event conditions");
        System.out.println(e.getStartDate());
        System.out.println(e.getEndDate());
        if (e.getStartDate().isAfter(e.getEndDate())){
            System.out.println("start date is after end date");
            return false;
        }
        System.out.println("event condition are happy");
        return true;
        //todo
    }

    private boolean isLoginValid(String login) {
        long count = userDao.getAll().stream().filter(u -> u.getLogin().equals(login)).count();
        return count == 0;
    }
}
