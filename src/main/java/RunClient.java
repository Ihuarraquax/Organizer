import client.ClientImpl;
import communication.Method;
import entities.User;

import java.util.Scanner;

public class RunClient {
    public static void main(String[] args) {

        ClientImpl client = new ClientImpl();
        Scanner scanner = new Scanner(System.in);

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
                    User user = new User("zabelko", "123123", "Hube", "Bube");
                    client.register(user);
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
    }
}
