package server;

import communication.Method;
import communication.ServerCommunicator;
import dao.EntityMenager;
import entities.Event;
import entities.User;

public class ClientThread implements Runnable {

    ServerCommunicator communicator;
    EntityMenager entityMenager;
    User user;

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
                String login = communicator.reciveString();
                String pass = communicator.reciveString();

                user = entityMenager.login(login, pass);

                communicator.sendUser(user);

                break;
            case REGISTER:
                User userToRegister = communicator.reciveUser();
                System.out.println("GOT USER TO REGISTRATION:");
                System.out.println(userToRegister);
                boolean happy = entityMenager.registerUser(userToRegister);
                System.out.println("USER REGISTERED? " + happy);
                communicator.sendComfirmation(happy);
                break;
            case POST:
                if (user != null) {
                    communicator.sendComfirmation(Boolean.TRUE);
                    Event event = communicator.reciveEvent();

                    happy = entityMenager.saveEvent(event);
                    System.out.println("EVENT ADDED? " + happy);
                    communicator.sendComfirmation(happy);


                } else {
                    communicator.sendComfirmation(Boolean.FALSE);
                    System.out.println("USER NOT LOGGED");
                }

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


