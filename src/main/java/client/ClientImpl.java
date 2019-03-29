package client;

import entities.Event;
import entities.User;
import org.pmw.tinylog.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientImpl implements ClientAPI {

    Socket socket;
    DataOutputStream out;
    Scanner scanner;
    public ClientImpl(String serverAddress, int port) {

        try {

            socket = new Socket(serverAddress, port);
            out = new DataOutputStream(socket.getOutputStream());

            scanner = new Scanner(System.in);
            Logger.info("Sending method from keyboard");
            out.writeInt(1);

            out.writeUTF("zabelko 123123 Hube Bube");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String login, String pass) {
        return null;
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public boolean post(Event event) {
        return false;
    }

    @Override
    public Event get(long id) {
        return null;
    }

    @Override
    public List<Event> getAll() {
        return null;
    }

    @Override
    public Event update(long id, Event event) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
