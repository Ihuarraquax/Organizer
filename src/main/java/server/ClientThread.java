package server;

import communication.Method;
import communication.ServerCommunicator;
import entities.Event;
import entities.User;

import javax.persistence.EntityManagerFactory;

public class ClientThread implements Runnable {

    ServerCommunicator c;
    User currentlyLoggedUser;

    OrganizerService organizerService;

    public ClientThread(ServerCommunicator ServerCommunicator, EntityManagerFactory entityManagerFactory) {
        this.c = ServerCommunicator;
        this.organizerService = new OrganizerService(entityManagerFactory);

    }

    @Override
    public void run() {
        while (true) {
            serve();
        }
    }

    private void serve() {
        Method method = c.reciveMethodAndWriteComfirmation();

        switch (method) {

            case LOGIN:
                String login = c.reciveString();
                String pass = c.reciveString();
                currentlyLoggedUser = organizerService.login(login, pass);
                c.sendUser(currentlyLoggedUser);
                break;
            case REGISTER:
                User userToRegister = c.reciveUser();
                System.out.println("GOT USER TO REGISTRATION:");
                System.out.println(userToRegister);
                boolean happy = organizerService.registerUser(userToRegister);
                System.out.println("USER REGISTERED? " + happy);
                c.sendComfirmation(happy);
                break;
            case POST:
                if (currentlyLoggedUser != null) {
                    c.sendComfirmation(Boolean.TRUE);
                    Event event = c.reciveEvent();

                    happy = organizerService.saveEvent(event);
                    System.out.println("EVENT ADDED? " + happy);
                    c.sendComfirmation(happy);
                } else {
                    c.sendComfirmation(Boolean.FALSE);
                    System.out.println("USER NOT LOGGED");
                }
                break;
            case GET:
                long id = c.reciveLong();
                c.sendEvent(organizerService.getEvent(id));
                break;
            case GETALL:
                c.sendEvents(organizerService.getAllEvents());
                break;
            case UPDATE:
                id = c.reciveLong();
                Event newEvent = c.reciveEvent();
                Event eventToUpdate = organizerService.getEvent(id);
                String[] params = getParams(newEvent);
                organizerService.update(eventToUpdate, params);
                break;
            case DELETE:
                id = c.reciveLong();
                Event event = organizerService.getEvent(id);
                if (event.getAuthor().getId() == currentlyLoggedUser.getId()) {
                    organizerService.delete(event);
                }
                break;
        }
    }

    private String[] getParams(Event newEvent) {
        return new String[]{newEvent.getName(), newEvent.getStartDate().toString(), newEvent.getEndDate().toString()};
    }
}


