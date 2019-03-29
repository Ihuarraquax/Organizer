package server;

import communication.Communicator;
import communication.Method;
import communication.RequestResolver;
import dao.EntityMenager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    RequestResolver requestResolver;

    public ClientThread(Socket clientSocket, EntityMenager entityMenager) {

        this.requestResolver = new RequestResolver(entityMenager);
        this.socket = clientSocket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {


        Method method = Communicator.getMethod();
        String body = Communicator.getBody();


//            Method method = Method.values()[in.readInt()];
//            String body = in.readUTF();


        requestResolver.resolve(method, body);
    }

}


