package communication;

import entities.Event;
import entities.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerCommunicator implements ServerCommunicatorAPI {

    public Socket socket;
    public ObjectInputStream in;
    public ObjectOutputStream out;

    public ServerCommunicator(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Method reciveMethod() throws IOException {

        Method method = null;
        try {
            method = (Method) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        comfirm(method);
        return method;

    }

    @Override
    public Method reciveMethodAndSendComfirmation() throws IOException {
        Method method = reciveMethod();
        sendComfirmation((method!=null));
        return method;
    }



    private void comfirm(Method method) throws IOException {
        if (method == null) out.writeObject(Boolean.FALSE);
        else out.writeObject(Boolean.TRUE);
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
    public void sendComfirmation(Boolean b) {
        try {
            out.writeObject(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public void sendUser(User user) {
        try {
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public void sendEvents(List<Event> allEvents) {
        try {
            out.writeObject(allEvents);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public long reciveLong() {
        try {
            return (Long) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public boolean socketIsClosed() {
        return socket.isClosed();
    }

    @Override
    public void sendEventSize(int size) {

    }

}
