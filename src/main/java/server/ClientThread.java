package server;

import communicator.Method;
import org.pmw.tinylog.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    public ClientThread(Socket clientSocket) {
        socket = clientSocket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {


        try {

            Logger.info("Reading method int");
            Method method = Method.values()[in.readInt()];
            Logger.info(method.name());
            switch (method) {
                case LOGIN:
                    break;
                case REGISTER:
                    break;
                case POST:
                    break;
                case GET:
                    break;
                case UPDATE:
                    break;
                case DELETE:
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


