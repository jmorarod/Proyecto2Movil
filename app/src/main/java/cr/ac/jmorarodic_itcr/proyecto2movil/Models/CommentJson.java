package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

import java.io.Serializable;

public class CommentJson {
    private User user;
    private String description;

    public CommentJson(User user, String description) {
        this.setUser(user);
        this.setDescription(description);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
