import client.ClientImpl;
import entities.User;

public class RunClient {
    public static void main(String[] args) {

        ClientImpl client = new ClientImpl();

        User user = new User("zabelko", "123123", "Hube", "Bube");

        System.out.println(client.register(user));

        client.login("zabelko", "123123");

    }
}
