package client;

import entities.Event;
import entities.User;

import java.util.List;

public interface ClientAPI {

    User login(String login, String pass);

    boolean register(User user);

    boolean post(Event event);

    Event get(long id);

    List<Event> getAll();

    Event update(long id, Event event);

    boolean delete(long id);


}
