package communication;

import dao.EntityMenager;
import entities.User;

import java.util.StringTokenizer;

public class RequestResolver {

    EntityMenager entityMenager;

    public RequestResolver(EntityMenager entityMenager) {
        this.entityMenager = entityMenager;
    }

    public void resolve(Method method, String body) {

        StringTokenizer stringTokenizer = new StringTokenizer(body);
        switch (method) {
            case LOGIN:

                break;
            case REGISTER:
                User user = new User(
                        stringTokenizer.nextToken(),
                        stringTokenizer.nextToken(),
                        stringTokenizer.nextToken(),
                        stringTokenizer.nextToken());
                entityMenager.saveUser(user);
                break;
            case POST:
                break;
            case GET:
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
        }

    }
}
