package communication;

import entities.Event;
import entities.EventType;
import entities.User;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ServerTextCommunicator implements ServerCommunicatorAPI {

    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;

    public ServerTextCommunicator(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendWelcomeMessage() {
        try {
            out.writeUTF("ORGANIZER PROTOCOL");
            out.writeUTF("Dostępne komendy:");

            for (int i = 0; i < Method.values().length; i++) {
                out.writeUTF(Method.getMethodFromValue(i).name());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Method reciveMethod() {
        Method method = null;
        try {
            method = Method.valueOf(in.readUTF().toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = Arrays.binarySearch(Method.values(), method);
        if (i > 0) {
            return method;
        }
        return null;
    }

    @Override
    public Method reciveMethodAndSendComfirmation() throws IOException {

        Method method = Method.valueOf(in.readUTF());
        sendComfirmation(true);
        return method;
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
    public void sendComfirmation(Boolean b) {

        try {
            if (b)
                out.writeUTF("1");
            else out.writeUTF("0");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String reciveString() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
    public Event reciveEvent() {
        try {
            Long id = Long.valueOf(in.readUTF());
            String name = in.readUTF();
            EventType eventType = EventType.valueOf(in.readUTF());
            User author = reciveUser();

            String description = in.readUTF();
            LocalDateTime startDate = LocalDateTime.parse(in.readUTF());
            LocalDateTime endDate = LocalDateTime.parse(in.readUTF());

            Event event = new Event(name, eventType, description, startDate, endDate, author);
            return event;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendEvents(List<Event> allEvents) {

        for (Event event : allEvents) {
            sendEvent(event);
        }

    }

    @Override
    public void sendEvent(Event event) {
        try {
            out.writeUTF(String.valueOf(event.getId()));
            out.writeUTF(event.getName());
            out.writeUTF(event.getType().toString());
            sendUser(event.getAuthor());
            out.writeUTF(event.getDescription());
            out.writeUTF(event.getEndDate().toString());
            out.writeUTF(event.getStartDate().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long reciveLong() {
        try {
            return Long.valueOf(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean socketIsClosed() {
        return false;
    }

    @Override
    public void sendEventSize(int size) {
        try {
            out.writeUTF(String.valueOf(size));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
