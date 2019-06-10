package communication;

import entities.Event;
import entities.User;

import java.io.IOException;
import java.util.List;

public interface ServerCommunicatorAPI {
    Method reciveMethod() throws IOException;

    Method reciveMethodAndSendComfirmation() throws IOException;

    User reciveUser();

    void sendComfirmation(Boolean b);

    String reciveString();

    void sendUser(User user);

    Event reciveEvent();

    void sendEvents(List<Event> allEvents);

    void sendEvent(Event event);

    long reciveLong();

    boolean socketIsClosed();

    void sendEventSize(int size);
}
