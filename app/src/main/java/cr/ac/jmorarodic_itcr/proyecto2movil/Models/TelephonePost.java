package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

public class TelephonePost {

    private String telephone;
    private int id;

    public TelephonePost(String telephone, int id) {
        this.setTelephone(telephone);
        this.setUser_id(id);
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getUser_id() {
        return id;
    }

    public void setUser_id(int user_id) {
        this.id = user_id;
    }
}
