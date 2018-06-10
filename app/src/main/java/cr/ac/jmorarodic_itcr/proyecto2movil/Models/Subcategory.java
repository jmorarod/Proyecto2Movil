package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

import java.io.Serializable;

public class Subcategory implements Serializable {
    private int id;
    private String name;
    private String description;
    private String photo;

    public Subcategory(String name, String description, String photo, int id) {
        this.setName(name);
        this.setDescription(description);
        this.setPhoto(description);
        this.setId(id);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
