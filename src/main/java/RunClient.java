import client.ClientImpl;
import communication.Method;
import entities.Event;
import entities.User;

import java.time.LocalDateTime;
import java.util.Scanner;

public class RunClient {
    public static void main(String[] args) {

        ClientImpl client = new ClientImpl();
        Scanner scanner = new Scanner(System.in);
        User user = new User("zabelko", "123123", "Hube", "Bube");
        Event event = new Event("Jackonalia", LocalDateTime.now(), LocalDateTime.now().plusHours(2L), user);

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
                    break;
                case POST:
                    client.post(event);
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
    }
}
