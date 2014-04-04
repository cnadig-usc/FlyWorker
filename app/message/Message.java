package message;

/**
 * Created by johntower on 2/11/14.
 */
public enum Message {
    ACCOUNT_NOT_FOUND ("Account not found for username"),
    WRONG_PASSWORD ("Incorrect password for username"),
    AUTHENTICATED ("Account successfully authenticated");

    Message(String msg) {
        this.msg = msg;
    }

    private String msg;

    @Override
    public String toString() {
        return msg;
    }
}