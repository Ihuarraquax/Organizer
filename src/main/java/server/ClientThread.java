package server;

import communication.Method;
import communication.ServerCommunicator;
import dao.EntityMenager;
import entities.User;

public class ClientThread implements Runnable {

    ServerCommunicator communicator;
    EntityMenager entityMenager;

    public ClientThread(ServerCommunicator ServerCommunicator, EntityMenager entityMenager) {
        this.communicator = ServerCommunicator;
        this.entityMenager = entityMenager;
    }

    @Override
    public void run() {
        while (true) {
            serve();
        }
    }

    private void serve() {
        Method method = communicator.reciveMethodAndWriteComfirmation();

        switch (method) {

            case LOGIN:
                break;
            case REGISTER:
                User userToRegister = communicator.reciveUser();
                System.out.println("GOT USER TO REGISTRATION:");
                System.out.println(userToRegister);
                boolean happy = entityMenager.registerUser(userToRegister);
                System.out.println("USER REGISTERED? "+happy);
                communicator.sendComfirmation(happy);
                break;
            case POST:
                break;
            case GET:
                break;
            case GETALL:
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
        }
    }

    private void register(User user) {

    }
}


