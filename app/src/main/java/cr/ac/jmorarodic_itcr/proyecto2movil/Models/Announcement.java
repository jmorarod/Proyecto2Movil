package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

import java.util.ArrayList;

public class Announcement {
    private int id;
    private String title;
    private String description;
    private float price;
    private String photo;
    private User user;
    private float latitude;
    private float longitude;
    private Subcategory subcategory;
    private String place;
    private ArrayList<FavoriteJson> favorites;
    private ArrayList<CommentJson> comments;

    public Announcement(String title, String description, float price, String photo, User user, float latitude, float longitude, Subcategory subcategory, String place, ArrayList<FavoriteJson> favorites, ArrayList<CommentJson> comments){
        this.setTitle(title);
        this.setDescription(description);
        this.setPrice(price);
        this.setPhoto(photo);
        this.setUser(user);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setSubcategory(subcategory);
        this.setPlace(place);
        this.setFavorites(favorites);
        this.setComments(comments);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ArrayList<FavoriteJson> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<FavoriteJson> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<CommentJson> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentJson> comments) {
        this.comments = comments;
    }
}
