package server;

import communication.ServerCommunicator;
import dao.EntityMenager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerImplTCP {

    ServerSocket serverSocket;
    List<Thread> clientThreadList;
    EntityMenager entityMenager;
    ServerCommunicator serverCommunicator;

    public ServerImplTCP() {
        entityMenager = new EntityMenager();
        clientThreadList = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(3334);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerCommunicator serverCommunicator = new ServerCommunicator(clientSocket);
                Thread clientThread = new Thread(new ClientThread(serverCommunicator, entityMenager));
                clientThread.start();
                clientThreadList.add(clientThread);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
