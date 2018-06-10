package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

import java.io.Serializable;

public class SubcategoryJson implements Serializable {

    private Subcategory subcategory;


    public SubcategoryJson(Subcategory subcategory) {
        this.setSubcategory(subcategory);
    }


    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
}
