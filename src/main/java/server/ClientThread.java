package server;

import communication.Communicator;
import communication.Method;
import dao.EntityMenager;
import entities.User;

public class ClientThread implements Runnable {

    Communicator communicator;
    EntityMenager entityMenager;

    public ClientThread(Communicator communicator, EntityMenager entityMenager) {
        this.communicator = communicator;
        this.entityMenager = entityMenager;
    }

    @Override
    public void run() {
        while (true) {
            serve();
        }
    }

    private void serve() {
        Method method = communicator.getMethod();

        switch (method) {

            case REGISTER:
                User user = (User) communicator.getBody();
                register(user);
                break;
            case LOGIN:
                String login = (String) communicator.getBody();

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
        boolean isHappy = entityMenager.saveUser(user);
        communicator.sendResponseToClient(isHappy);
    }
}


