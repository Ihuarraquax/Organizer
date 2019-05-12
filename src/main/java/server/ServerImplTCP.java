package server;

import communication.ServerCommunicator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerImplTCP {

    ServerSocket serverSocket;
    List<Thread> clientThreadList;

    public ServerImplTCP() {
        clientThreadList = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("organizer");

        try {
            serverSocket = new ServerSocket(3334);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerCommunicator serverCommunicator = new ServerCommunicator(clientSocket);

                Thread clientThread = new Thread(new ClientThread(serverCommunicator, entityManagerFactory));
                clientThread.start();
                clientThreadList.add(clientThread);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
