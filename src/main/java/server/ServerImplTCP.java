package server;

import communication.Communicator;
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
    Communicator communicator;

    public ServerImplTCP() {
        entityMenager = new EntityMenager();
        clientThreadList = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(3333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Communicator communicator = new Communicator(clientSocket);
                Thread clientThread = new Thread(new ClientThread(communicator, entityMenager));
                clientThread.start();
                clientThreadList.add(clientThread);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
