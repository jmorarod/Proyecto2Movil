package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.graphics.Bitmap;

/**
 * Created by josem on 3/6/2018.
 */

public class AnuncioItem {
    private String descripcion;
    private String precio;
    private int id;
    private Bitmap imagen;
    private int imageResource;

    public AnuncioItem(String descripcion, String precio) {
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public AnuncioItem(String descripcion, String precio, Bitmap imagen) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }

    public AnuncioItem(String descripcion, String precio, int imageResource) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.imageResource = imageResource;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
