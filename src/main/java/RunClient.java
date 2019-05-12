import client.ClientImpl;
import communication.Method;
import entities.Event;
import entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class RunClient {
    public static void main(String[] args) {

        ClientImpl client = new ClientImpl();
        Scanner scanner = new Scanner(System.in);
        User user = new User("zabelko", "123123", "Hube", "Bube");
        User user2 = new User("zabi", "123123", "Ihu", "arraquax");

        Event event = new Event("Jackonalia", LocalDateTime.now(), LocalDateTime.now().plusHours(2L), user);
        Event event2 = new Event("nowyevent", LocalDateTime.now(), LocalDateTime.now().plusHours(3L), user);
        Event event3 = new Event("raid", LocalDateTime.now(), LocalDateTime.now().plusHours(3L), user2);

        client.register(user);
        client.register(user2);
        client.login("zabelko", "123123");
        client.post(event);
        client.post(event3);


        int i=0;
        for (Method value : Method.values()) {
            System.out.println(i++ +": "+value);
        }

        while (true) {
            Method method = Method.values()[scanner.nextInt()];
            switch (method) {
                case LOGIN:
                    System.out.println("Podaj login");
                    String login = scanner.next();
                    System.out.println("Podaj haslo");
                    String pass = scanner.next();
                    client.login(login, pass);
                    break;
                case REGISTER:
                    client.register(user);
                    client.register(user2);
                    break;
                case POST:
                    client.post(event);
                    break;
                case GET:
                    System.out.println("id: ");
                    Event eventFromGet = client.get(scanner.nextInt());
                    System.out.println(eventFromGet);

                    break;
                case GETALL:
                    List<Event> events = client.getAll();
                    for (Event event1 : events) {
                        System.out.println(event1);
                    }
                    break;
                case UPDATE:
                    client.update(3, event2);
                    break;
                case DELETE:
                    client.delete(3);
                    break;
            }
        }
    }
}
