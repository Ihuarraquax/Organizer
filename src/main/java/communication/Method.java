package communication;


/**
 * Enum zawierający komendy protokołu komunikacyjnego.
 */
public enum Method {
    LOGIN(0),REGISTER(1),POST(2),GET(3),GETALL(4),UPDATE(5),DELETE(6);

    int value;

    Method(int value) {
        this.value = value;
    }

    public static Method getMethodFromValue(int value) {
        return Method.values()[value];
    }
}
