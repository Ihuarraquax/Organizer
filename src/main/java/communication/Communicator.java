package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// klasa pobierajaca i wysylajaca dane miedzy klientem a serverem;
public class Communicator {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Communicator(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Method getMethod() {
        try {
            return Method.values()[in.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBody() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
