import dao.EntityMenager;
import entities.Event;
import entities.User;
import server.ServerImpl;

import java.time.LocalDateTime;

public class RunServer {
    public static void main(String[] args) {

        EntityMenager entityMenager = new EntityMenager();

        User user = new User("zablo", "123", "Hubert", "Zablocki");
        User user2 = new User("diabolo", "123123", "Huby", "Zabel");
        Event event = new Event(
                "jackonalia",
                LocalDateTime.of(2020, 10, 14, 20, 30),
                LocalDateTime.of(2020, 10, 17, 21, 50),
                user);
        Event event2 = new Event(
                "jack",
                LocalDateTime.of(2020, 10, 14, 20, 30),
                LocalDateTime.of(2020, 10, 17, 21, 50),
                user);
        
        entityMenager.saveUser(user);
        entityMenager.saveUser(user2);
        entityMenager.saveEvent(event);
        entityMenager.saveEvent(event2);

        for (Event userEvent : user.getEvents()) {
            System.out.println(userEvent);
        }


        ServerImpl server = new ServerImpl();
    }
}
