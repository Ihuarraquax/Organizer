package server;


import dao.EventDao;
import dao.UserDao;
import entities.Event;
import entities.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

/**
 * Klasa zapewniająca poprawność pobieranych oraz zapisywanych danych z DB i formatów, w których zwracane są wyniki
 */

public class OrganizerService {

    UserDao userDao;
    EventDao eventDao;

    public OrganizerService(EntityManagerFactory entityManagerFactory) {
        userDao = new UserDao(entityManagerFactory);
        eventDao = new EventDao(entityManagerFactory);
    }

    /**
     * Walidacja użytkownika, sprawdza czy login znajduję się w bazie danych
     * @param user
     * @return true - user dodany do bazy, false - nie przeszedł walidacji
     */
    public boolean registerUser(User user) {

        if (isLoginValid(user.getLogin())) {
            userDao.save(user);
            return true;
        }
        return false;
    }

    /**
     * Walidacja eventu, sprawdza warunki:
     * - data startu jest wcześniejsza niz data zakończenia
     * - nazwa nie jest zajęta
     * @param event wydarzenie
     * @return true - event dodany do bazy, false - nie przeszedł walidacji
     */
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

    /**
     * Sprawdza, czy w bazie istnieje user o podanym loginie i hasle
     * @param login login
     * @param pass hasło
     * @return użytkownik o podanym loginie i haśle
     */
    public User login(String login, String pass) {
        Optional<User> user = userDao.getAll().stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(pass)).findFirst();
        return user.orElse(null);
    }

    /**
     *
     * @param id identyfikator wydarzenia
     * @return zwraca obiekt rządanego wydarzenia, w przypadku braku w bazie danych zwraca nulla;
     */
    public Event getEvent(long id) {
        return eventDao.get(id).orElse(null);
    }

    /**
     * Uaktualnia wydarzenie
     * @param event wydarzenie do uaktualnienia
     * @param params tablica parametrów z wartościami do uaktualnienia. 0 - nazwa, 1 - data startu, 2 - data zakonczenia
     */
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
