package client;

import entities.Event;
import entities.User;

import java.util.List;

public interface ClientAPI {
    /**
     * Wysyła rządanie logowania
     * @param login login
     * @param pass hasło
     * @return użytkownik o podanym loginie i haśle
     */
    User login(String login, String pass);

    /**
     * Rejestruje użytkownika
     * @param user użytkownik do zarejestrowania
     * @return true - użytkownik zarejestrowany pomyślnie, false - nie udało się zajerestrować
     */
    boolean register(User user);


    boolean post(Event event);

    Event get(long id);

    List<Event> getAll();

    void update(long id, Event event);

    boolean delete(long id);


}
