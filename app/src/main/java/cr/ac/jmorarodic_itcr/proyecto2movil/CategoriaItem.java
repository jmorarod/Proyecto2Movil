package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.graphics.Bitmap;

/**
 * Created by josem on 3/6/2018.
 */

public class CategoriaItem {
    private String nombre;
    private String descripcion;
    private Bitmap imagen;
    private int imageResource;

    public CategoriaItem(String nombre, String descripcion, Bitmap imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }
    public CategoriaItem(String nombre, String descripcion, int imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imageResource = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
}
