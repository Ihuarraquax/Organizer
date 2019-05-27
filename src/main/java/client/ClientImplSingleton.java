package client;

public class ClientImplSingleton {
    private static ClientAPI instance;

    private ClientImplSingleton() {
    }

    public static synchronized ClientAPI getInstance() {
        if (instance == null) {
            instance = new ClientImpl();
        }

        return instance;
    }

}
