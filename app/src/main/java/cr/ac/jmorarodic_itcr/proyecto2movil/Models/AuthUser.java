package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

public class AuthUser {
    private String auth_token;
    private int id;
    private String name;
    private String email;
    private String photo;
    private String role;

    public AuthUser(){}

    public AuthUser(String auth_token, int id, String name, String email, String photo, String role) {
        this.setAuth_token(auth_token);
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPhoto(photo);
        this.setRole(role);
    }


    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
