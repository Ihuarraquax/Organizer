package communication.server;

import communication.Method;
import entities.Event;
import entities.User;

import java.io.IOException;
import java.util.List;


/**
 * API communicatora
 */
public interface ServerCommunicatorAPI {
    Method reciveMethod();

    Method reciveMethodAndSendComfirmation() throws IOException;

    User reciveUser();

    void sendComfirmation(Boolean b);

    String reciveString();

    void sendUser(User user);

    Event reciveEvent();

    void sendEvents(List<Event> allEvents);

    void sendEvent(Event event);

    long reciveLong();

    void sendEventSize(int size);
}
