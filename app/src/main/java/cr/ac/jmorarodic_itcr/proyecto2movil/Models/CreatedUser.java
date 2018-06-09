package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

public class CreatedUser {
    private User user;
    private String auth_token;

    public CreatedUser(User user, String auht_token) {
        this.setUser(user);
        this.setAuth_token(auht_token);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auht_token) {
        this.auth_token = auht_token;
    }
}
