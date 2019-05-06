package communication;

import entities.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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


    public Method reciveMethodAndWriteComfirmation() {
        try {
            Method method = (Method) in.readObject();
            comfirm(method);
            return method;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
}
