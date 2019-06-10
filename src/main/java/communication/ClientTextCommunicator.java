package communication;

import entities.Event;
import entities.EventType;
import entities.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientTextCommunicator implements ClientCommunicatorAPI {


    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;

    public ClientTextCommunicator(String server, int port) {
        try {
            this.socket = new Socket(server, port);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void sendMethod(Method method) {
        try {
            out.writeUTF(method.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean reviceComfirmation() {
        try {

            return (in.readUTF().equals("1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendUser(User user) {
        try {
            out.writeUTF(user.getLogin());
            out.writeUTF(user.getPassword());
            out.writeUTF(user.getEmail());
            out.writeUTF(user.getFirstName());
            out.writeUTF(user.getLastName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendLoginInformations(String login, String pass) {
        try {
            System.out.println("sending login");
            out.writeUTF(login);
            System.out.println("sending pass");
            out.writeUTF(pass);
            System.out.println("getting comfirmation");
            boolean comfirmation = reviceComfirmation();
            System.out.println(comfirmation);
            return comfirmation;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sendLoginInformations() failed");
        return false;
    }

    @Override
    public User reciveUser() {
        try {
            String login = in.readUTF();
            String password = in.readUTF();
            String email = in.readUTF();
            String firstName = in.readUTF();
            String lastName = in.readUTF();
            User user = new User(login, password, email, firstName, lastName);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void sendEvent(Event event) {
        try {
            out.writeUTF(Long.toString(event.getId()));
            out.writeUTF(event.getName());
            out.writeUTF(event.getType().toString());

            sendUser(event.getAuthor());

            out.writeUTF(event.getDescription());
            out.writeUTF(event.getStartDate().toString());
            out.writeUTF(event.getEndDate().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Event> reciveEventList() {
        List<Event> list = new ArrayList<>();
        int size = reciveEventsSize();
        System.out.println("pobieram eventy");
        for (int i = 0; i < size; i++) {
            Event event = reciveEvent();
            list.add(event);
        }
        return list;

    }

    private int reciveEventsSize() {
        try {
            return Integer.valueOf(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Event reciveEvent() {
        try {

            Long id = Long.valueOf(in.readUTF());
            String name = in.readUTF();
            EventType eventType = EventType.valueOf(in.readUTF());
            User author = reciveUser();

            String description = in.readUTF();
            LocalDateTime startDate = LocalDateTime.parse(in.readUTF());
            LocalDateTime endDate = LocalDateTime.parse(in.readUTF());

            Event event = new Event(id, name, eventType, description, startDate, endDate, author);
            return event;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Event getEvent(long id) {
        try {
            out.writeUTF(Long.toString(id));
            return reciveEvent();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Event event) {
        try {
            out.writeUTF(Long.toString(id));
            sendEvent(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            out.writeUTF(Long.toString(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendMethodAndGetComfirmation(Method method) {
        sendMethod(method);
        return reviceComfirmation();
    }
}
