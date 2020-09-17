package nhl.hd.tv.login;

public class LoginViewState {
    boolean loading;
    String username;
    String password;
    String error;

    public LoginViewState(boolean loading, String username, String password, String error) {
        this.loading = loading;
        this.username = username;
        this.password = password;
        this.error = error;
    }
}
