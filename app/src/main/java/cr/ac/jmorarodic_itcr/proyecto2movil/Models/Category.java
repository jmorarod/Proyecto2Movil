package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;
    private String description;
    private String photo;
    private ArrayList<SubcategoryJson> subcategories;

    public Category(String name, String description, String photo, int id, ArrayList<SubcategoryJson> subcategories) {
        this.setName(name);
        this.setDescription(description);
        this.setPhoto(photo);
        this.setId(id);
        this.setSubcategories(subcategories);
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ArrayList<SubcategoryJson> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(ArrayList<SubcategoryJson> subcategories) {
        this.subcategories = subcategories;
    }
}
