package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.graphics.Bitmap;

public class ComentarioItem {
    private String userName;
    private String comentario;
    private String image;
    private Bitmap userImage;
    private int userImageResource;

    public ComentarioItem(String userName, String comentario, Bitmap userImage) {
        this.userName = userName;
        this.comentario = comentario;
        this.userImage = userImage;

    }
    public ComentarioItem(String userName, String comentario, int userImageResource) {
        this.userName = userName;
        this.comentario = comentario;
        this.userImage = null;
        this.userImageResource = userImageResource;

    }

    public ComentarioItem(String userName, String comentario) {
        this.userName = userName;
        this.comentario = comentario;
        userImage = null;
    }

    public ComentarioItem(String userName, String comentario, String image) {
        this.userName = userName;
        this.comentario = comentario;
        this.setImage(image);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }

    public int getUserImageResource() {
        return userImageResource;
    }

    public void setUserImageResource(int userImageResource) {
        this.userImageResource = userImageResource;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
