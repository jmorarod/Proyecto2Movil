package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

public class EmailPost{

    private String email;
    private int user_id;

    public EmailPost(String email, int user_id) {
        this.setEmail(email);
        this.setUser_id(user_id);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
