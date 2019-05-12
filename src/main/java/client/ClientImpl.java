package client;

import communication.ClientCommunicator;
import communication.Method;
import entities.Event;
import entities.User;

import java.util.List;

public class ClientImpl implements ClientAPI {

    ClientCommunicator clientCommunicator;
    User user;

    public ClientImpl() {
        clientCommunicator = new ClientCommunicator("localhost", 3334);
    }

    // rejestruje usera i zwraca potwierdzenie, jesli sie udało true
    @Override
    public boolean register(User user) {
        System.out.println("REGISTERING USER");
        if (clientCommunicator.sendMethodAndGetComfirmation(Method.REGISTER)) {
            clientCommunicator.sendUser(user);
            if (clientCommunicator.reviceComfirmation()) {
                System.out.println("USER REGISTRATION SUCCESS");
                return true;
            } else {
                System.out.println("USER REGISTRATION FAILED");
                return false;
            }

        } else return false;
    }


    @Override
    public User login(String login, String pass) {

        if (clientCommunicator.sendMethodAndGetComfirmation(Method.LOGIN)) {
            System.out.println("+SENDING LOGIN AND PASS");
            if (clientCommunicator.sendLoginInformations(login, pass)) {
                System.out.println("+LOGGIN AND PASSWORD SEND SUCCESS");
                user = clientCommunicator.reciveUser();
                if (user == null) {
                    System.out.println("-LOGGING FAILED");
                    return null;
                }
                System.out.println("LOGGED AS: " + user);
                return user;
            } else {
                System.out.println("+LOGGIN AND PASSWORD SEND FAILED");
            }
        }
        return null;
    }

    @Override
    public boolean post(Event event) {
        if (clientCommunicator.sendMethodAndGetComfirmation(Method.POST)) {
            if (!clientCommunicator.reviceComfirmation()) {
                System.out.println("YOU ARE NOT LOGGED IN");
                return false;
            }
            clientCommunicator.sendEvent(event);

            if (clientCommunicator.reviceComfirmation()) {
                System.out.println(event.getName() + " +ADDED SUCCESS");
                return true;
            } else {
                System.out.println(event.getName() + " -ADDED FAILED");
                return false;
            }

        }
        return false;
    }

    @Override
    public Event get(long id) {
        if (clientCommunicator.sendMethodAndGetComfirmation(Method.GET)) {
            return clientCommunicator.reciveEvent(id);
        }
        return null;
    }

    @Override
    public List<Event> getAll() {
        if (clientCommunicator.sendMethodAndGetComfirmation(Method.GETALL)) {
            return clientCommunicator.reciveEventList();
        }
        return null;
    }

    @Override
    public void update(long id, Event newEvent) {
        if (clientCommunicator.sendMethodAndGetComfirmation(Method.UPDATE)) {
            clientCommunicator.update(id,newEvent);
        }
    }

    @Override
    public boolean delete(long id) {
        if (clientCommunicator.sendMethodAndGetComfirmation(Method.DELETE)) {
            clientCommunicator.delete(id);

        }
        return false;
    }
}
