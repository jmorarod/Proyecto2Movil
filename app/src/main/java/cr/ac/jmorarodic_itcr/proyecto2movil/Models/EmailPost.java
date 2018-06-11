package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

public class EmailPost{

    private String email;
    private int id;

    public EmailPost(String email, int id) {
        this.setEmail(email);
        this.setUser_id(id);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUser_id() {
        return id;
    }

    public void setUser_id(int user_id) {
        this.id = user_id;
    }
}
