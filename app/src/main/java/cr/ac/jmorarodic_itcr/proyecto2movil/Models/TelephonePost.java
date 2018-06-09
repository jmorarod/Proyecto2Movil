package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

public class TelephonePost {

    private String telephone;
    private int user_id;

    public TelephonePost(String telephone, int user_id) {
        this.setTelephone(telephone);
        this.setUser_id(user_id);
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
