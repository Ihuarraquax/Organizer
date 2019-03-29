package server;

import communicator.Method;
import entities.User;

public interface ServerAPI {

    void response(Method method, String body);
}
