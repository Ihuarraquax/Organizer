package communication;

import entities.User;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMethod(Method method) {
        try {
            out.writeObject(method);
            return reviceComfirmation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean reviceComfirmation() {

        try {
            return (Boolean) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendUser(User user) {
        try {
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
