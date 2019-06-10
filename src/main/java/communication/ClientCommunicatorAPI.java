package communication;

import entities.Event;
import entities.User;

import java.util.List;

public interface ClientCommunicatorAPI {
    void sendMethod(Method method);

    boolean reviceComfirmation();

    void sendUser(User user);

    boolean sendLoginInformations(String login, String pass);

    User reciveUser();

    void sendEvent(Event event);

    List<Event> reciveEventList();

    Event reciveEvent();

    Event getEvent(long id);

    void update(long id, Event event);

    void delete(long id);

    boolean sendMethodAndGetComfirmation(Method method);
}
