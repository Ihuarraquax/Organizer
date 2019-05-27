package communication;

import entities.Event;
import entities.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerCommunicator {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerCommunicator(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Method reciveMethodAndWriteComfirmation() throws IOException, ClassNotFoundException {

        Method method = (Method) in.readObject();
        comfirm(method);
        return method;

    }

    private void comfirm(Method method) throws IOException {
        if (method == null) out.writeObject(Boolean.FALSE);
        else out.writeObject(Boolean.TRUE);
    }

    public User reciveUser() {
        try {
            User user = (User) in.readObject();
            return user;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendComfirmation(Boolean b) {
        try {
            out.writeObject(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String reciveString() {
        try {
            String string = (String) in.readObject();
            sendComfirmation(Boolean.TRUE);
            return string;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendUser(User user) {
        try {
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Event reciveEvent() {
        try {
            Event event = (Event) in.readObject();
            System.out.println("RECIVED EVENT FROM COMMUNICATOR: " + event);
            return event;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendEvents(List<Event> allEvents) {
        try {
            out.writeObject(allEvents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEvent(Event event) {
        try {
            out.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long reciveLong() {
        try {
            return (Long) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void sendStatus(String message) {

    }

    public boolean connectionClosed() {
        return socket.isClosed();
    }
}
