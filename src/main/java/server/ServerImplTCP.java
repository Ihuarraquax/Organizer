package server;

import communication.Method;
import communication.server.ServerTextCommunicator;
import entities.Event;
import entities.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *  Tworzy sockety z łączącymi się klientami, przechowuje je w liście, dla każdego tworzy osobny wyjątek ClientThread
 */
class ServerImplTCP {

    private ServerSocket serverSocket;
    private List<Thread> clientThreadList;

    ServerImplTCP() {
        clientThreadList = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("organizer");

        try {
            serverSocket = new ServerSocket(3334);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerTextCommunicator serverCommunicator = new ServerTextCommunicator(clientSocket);

                Thread clientThread = new Thread(new ClientThread(serverCommunicator, entityManagerFactory));
                clientThread.start();
                clientThreadList.add(clientThread);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  Wątek obsugujący rządania klientów
     */

    public class ClientThread implements Runnable {

        ServerTextCommunicator c;
        User currentlyLoggedUser;

        OrganizerService organizerService;


        /**
         *
         * @param communicator
         * @param entityManagerFactory
         */

        ClientThread(ServerTextCommunicator communicator, EntityManagerFactory entityManagerFactory) {
            this.c = communicator;
            this.organizerService = new OrganizerService(entityManagerFactory);

        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    serve();
                }
            } finally {
                closeConnections();
            }

        }

        /**
         * Obsługuję rządania klienta.
         */
        private void serve() {
            Method method;
            try {
                method = c.reciveMethodAndSendComfirmation();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " is closed");
                Thread.currentThread().interrupt();
                return;
            }

            switch (method) {

                case LOGIN:
                    String login = c.reciveString();
                    System.out.println("login: " + login);
                    String pass = c.reciveString();
                    System.out.println("pass: " + pass);
                    if (login == null || pass == null) {
                        c.sendComfirmation(false);
                    } else {
                        c.sendComfirmation(true);
                        currentlyLoggedUser = organizerService.login(login, pass);
                        c.sendUser(currentlyLoggedUser);
                    }
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
                        User author = event.getAuthor();
                        User exisitingUser = organizerService.login(author.getLogin(), author.getPassword());
                        if(!Objects.isNull(exisitingUser)){
                            event.setAuthor(exisitingUser);
                        }

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
                    List<Event> allEvents = organizerService.getAllEvents();
                    for (Event allEvent : allEvents) {
                        System.out.println(allEvent);
                    }
                    c.sendEventSize(allEvents.size());
                    c.sendEvents(allEvents);
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

        /**
         * Zamyka połączenia strumieni, socketow oraz usuwa się z listy aktywnych wątków
         */
        private synchronized void closeConnections() {

            if (c.socket != null) {
                try {
                    c.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (c.in != null) {
                try {
                    c.in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (c.out != null) {
                try {
                    c.out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                clientThreadList.remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private String[] getParams(Event newEvent) {
            return new String[]{newEvent.getName(), newEvent.getStartDate().toString(), newEvent.getEndDate().toString()};
        }
    }
}
