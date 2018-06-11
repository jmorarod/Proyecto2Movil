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
    private String imagenS;
    private String titulo;
    private float precioF;
    private int usuario;
    private float latitud;
    private float longitud;
    private int subcategoria;
    private String lugar;
    private int autor;

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

    public AnuncioItem(String descripcion, String imagenS, String titulo) {
        this.descripcion = descripcion;
        this.imagenS = imagenS;
        this.titulo = titulo;
    }

    public AnuncioItem(int id, String titulo, String descripcion, float precioF, String imagenS, int usuario, float latitud, float longitud, int subcategoria, String lugar, int autor) {
        this.id = id;
        this.setTitulo(titulo);
        this.descripcion = descripcion;
        this.setPrecioF(precioF);
        this.setImagenS(imagenS);
        this.setUsuario(usuario);
        this.setLatitud(latitud);
        this.setLongitud(longitud);
        this.setSubcategoria(subcategoria);
        this.setLugar(lugar);
        this.setAutor(autor);
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

    public String getImagenS() {
        return imagenS;
    }

    public void setImagenS(String imagenS) {
        this.imagenS = imagenS;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getPrecioF() {
        return precioF;
    }

    public void setPrecioF(float precioF) {
        this.precioF = precioF;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public int getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(int subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }
}
