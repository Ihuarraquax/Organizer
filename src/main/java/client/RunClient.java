package client;

import communication.Method;
import entities.Event;
import entities.EventType;
import entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 *  Klasa testująca metody ClientImpl
 */
public class RunClient {
    public static void main(String[] args) {

        ClientImpl client = new ClientImpl();
        Scanner scanner = new Scanner(System.in);
        User user = new User("zabelko", "123123", "zablo432432@o2.pl", "Hube", "Bube");
        User user2 = new User("andzej", "123123","andzejbober@o2.pl", "Andrzej", "Bober");
        User user3 = new User("qwerty", "123","qwerty123@o2.pl", "Kamil", "Nowak");

        Event event = new Event("Jackonalia", EventType.PARTY, "juwenalia studenckie UPH",  LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(9), user);
        Event event2 = new Event("Urodziny mamy",EventType.CALL , "zadzwon do mamy i złóz życzenia", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(3), user2);
        Event event3 = new Event("Raid",EventType.PARTY, "icc 25 hc", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), user2);
        Event event4 = new Event("Zebranie w firmie",EventType.BUSINESS, "Przynieść ze sobą dokumenty", LocalDateTime.now().plusWeeks(1), LocalDateTime.now().plusWeeks(1).plusHours(3), user3);


        client.register(user);
        client.register(user2);
        client.register(user3);
        client.login("zabelko", "123123");
        client.post(event);
        client.post(event2);
        client.post(event3);
        client.post(event4);

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
