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

    Communicator communicator;
    RequestResolver requestResolver;

    public ClientThread(Communicator communicator, EntityMenager entityMenager) {

        this.requestResolver = new RequestResolver(entityMenager);
        this.communicator = communicator;
    }

    @Override
    public void run() {
        while (true) {
            serve();
        }
    }

    private void serve() {
        Method method = communicator.getMethod();
        String body = communicator.getBody();
        requestResolver.resolve(method, body);
    }

}


