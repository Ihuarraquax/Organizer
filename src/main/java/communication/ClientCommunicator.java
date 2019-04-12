package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientCommunicator {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientCommunicator(String server, int port) {
        try {
            this.socket = new Socket(server, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            socket.setSoTimeout(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Method method, Object object) {
        send(method);
        send(object);
    }

    public void send(Method method) {
        Integer methodValue = method.getValue();
        try {
            out.writeObject(methodValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Object object) {
        try {
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean reciveBoolean() {
        try {
            return (Boolean) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object reciveObject() {
        try {
           return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
