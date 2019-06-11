package client;

import communication.client.ClientTextCommunicator;
import communication.Method;
import entities.Event;
import entities.User;

import java.util.List;


public class ClientImpl implements ClientAPI {

    ClientTextCommunicator c;
    User user;

    public ClientImpl() {
        System.out.println("Łaczenie z serverem");
        c = new ClientTextCommunicator("localhost", 3333);
    }

    // rejestruje usera i zwraca potwierdzenie, jesli sie udało true
    @Override
    public boolean register(User user) {

        System.out.println("sending method and waiting for comfirmation");
        if (c.sendMethodAndGetComfirmation(Method.REGISTER)) {
            System.out.println("sending user");
            c.sendUser(user);
            System.out.println("reciving comfirmation");
            if (c.reviceComfirmation()) {
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

        if (c.sendMethodAndGetComfirmation(Method.LOGIN)) {
            System.out.println("+SENDING LOGIN AND PASS");
            if (c.sendLoginInformations(login, pass)) {
                System.out.println("+LOGGIN AND PASSWORD SEND SUCCESS");
                user = c.reciveUser();
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
        if (c.sendMethodAndGetComfirmation(Method.POST)) {
            if (!c.reviceComfirmation()) {
                System.out.println("YOU ARE NOT LOGGED IN");
                return false;
            }
            c.sendEvent(event);

            if (c.reviceComfirmation()) {
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
        if (c.sendMethodAndGetComfirmation(Method.GET)) {
            return c.getEvent(id);
        }
        return null;
    }

    @Override
    public List<Event> getAll() {
        if (c.sendMethodAndGetComfirmation(Method.GETALL)) {
            return c.reciveEventList();
        }
        return null;
    }

    @Override
    public void update(long id, Event newEvent) {
        if (c.sendMethodAndGetComfirmation(Method.UPDATE)) {
            c.update(id,newEvent);
        }
    }

    @Override
    public boolean delete(long id) {
        if (c.sendMethodAndGetComfirmation(Method.DELETE)) {
            c.delete(id);

        }
        return false;
    }
}
