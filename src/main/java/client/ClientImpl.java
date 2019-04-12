package client;

import communication.ClientCommunicator;
import communication.Method;
import entities.Event;
import entities.User;

import java.util.List;

public class ClientImpl implements ClientAPI {

    ClientCommunicator communicator;

    public ClientImpl() {
        communicator = new ClientCommunicator("localhost", 3333);
    }

    @Override
    public boolean register(User user) {
        communicator.send(Method.REGISTER, user);
        return communicator.reciveBoolean();
    }

    @Override
    public User login(String login, String pass) {
        communicator.send(Method.LOGIN);
        communicator.send(login);
        communicator.send(pass);
        return (User) communicator.reciveObject();
    }

    @Override
    public boolean post(Event event) {
        return false;
    }

    @Override
    public Event get(long id) {
        return null;
    }

    @Override
    public List<Event> getAll() {
        return null;
    }

    @Override
    public Event update(long id, Event event) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
