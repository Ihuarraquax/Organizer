package communication;

import entities.Event;
import entities.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientCommunicator implements ClientCommunicatorAPI {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientCommunicator(String server, int port) {

        try {
            this.socket = new Socket(server, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendMethodAndGetComfirmation(Method method) {
        try {
            out.writeObject(method);
            return reviceComfirmation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendMethod(Method method) {

    }

    @Override
    public boolean reviceComfirmation() {

        try {
            return (Boolean) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendUser(User user) {
        try {
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean sendLoginInformations(String login, String pass) {
        try {
            out.writeObject(login);
            if (reviceComfirmation()) {
                out.writeObject(pass);
            }
            return reviceComfirmation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User reciveUser() {
        try {
            User user = (User) in.readObject();
            return user;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendEvent(Event event) {
        try {
            out.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Event> reciveEventList() {
        try {
            return (List<Event>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Event reciveEvent() {
        return null;
    }


    @Override
    public Event getEvent(long id) {
        try {
            out.writeObject(id);
            return (Event) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Event event) {
        try {
            out.writeObject(id);
            out.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(long id) {
        try {
            out.writeObject(id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
