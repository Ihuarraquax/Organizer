package client;

import communication.ClientCommunicator;
import communication.Method;
import entities.Event;
import entities.User;

import java.util.List;

public class ClientImpl implements ClientAPI {

    ClientCommunicator clientCommunicator;

    public ClientImpl() {
        clientCommunicator = new ClientCommunicator("localhost", 3333);
    }

    // rejestruje usera i zwraca potwierdzenie, jesli sie udało true
    @Override
    public boolean register(User user) {
        System.out.println("REGISTERING USER");
        if (clientCommunicator.sendMethod(Method.REGISTER)) {
            clientCommunicator.sendUser(user);
            if(clientCommunicator.reviceComfirmation()){
                System.out.println("USER REGISTRATION SUCCESS");
                return true;
            }
            else {
                System.out.println("USER REGISTRATION FAILED");
                return false;
            }

        } else return false;
    }


    @Override
    public User login(String login, String pass) {
        return null;
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
