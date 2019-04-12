package communication;

import java.io.*;
import java.net.Socket;

// klasa pobierajaca i wysylajaca dane miedzy klientem a serverem;
public class Communicator {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Communicator(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Method getMethod() {

        int methodValue;
        try {
            methodValue = (Integer) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return Method.getMethodFromValue(methodValue);

    }

    public Object getBody() {

        try {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendResponseToClient(Object response) {
        try {
            out.writeObject((Boolean) response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
