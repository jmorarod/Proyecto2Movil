package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private String password_digest;
    private String photo;
    private String role;
    private ArrayList<FavoriteJson> favorites;
    private ArrayList<Announcement> announcements;
    private ArrayList<TelephonePost> telephones;
    private ArrayList<EmailPost> emails;

    public User(int id, String name, String email, String password_digest, String photo, String role) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPassword_digest(password_digest);
        this.setPhoto(photo);
        this.setRole(role);
    }

    public User(int id, String name, String email, String password_digest, String photo, String role, ArrayList<FavoriteJson> favorites, ArrayList<Announcement> announcements, ArrayList<TelephonePost> telephones, ArrayList<EmailPost> emails) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPassword_digest(password_digest);
        this.setPhoto(photo);
        this.setRole(role);
        this.setAnnouncements(announcements);
        this.setFavorites(favorites);
        this.setTelephones(telephones);
        this.setEmails(emails);
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

    public String getPassword_digest() {
        return password_digest;
    }

    public void setPassword_digest(String password_digest) {
        this.password_digest = password_digest;
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


    public ArrayList<FavoriteJson> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<FavoriteJson> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }

    public ArrayList<TelephonePost> getTelephones() {
        return telephones;
    }

    public void setTelephones(ArrayList<TelephonePost> telephones) {
        this.telephones = telephones;
    }

    public ArrayList<EmailPost> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<EmailPost> emails) {
        this.emails = emails;
    }
}
