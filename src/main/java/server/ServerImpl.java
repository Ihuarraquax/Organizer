package server;

import communicator.Method;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl implements ServerAPI {

    ServerSocket serverSocket;
    List<Thread> clientThreadList;

    public ServerImpl() {

        clientThreadList = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(3333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientThread(clientSocket));
                clientThread.start();
                clientThreadList.add(clientThread);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void response(Method method, String body) {

    }
}
