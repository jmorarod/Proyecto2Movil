package cr.ac.jmorarodic_itcr.proyecto2movil.Models;

public class FavoriteJson {
    private int id;
    private User user;
    private Announcement announcement;

    public FavoriteJson(int id, User user, Announcement announcement) {
        this.setId(id);
        this.setUser(user);
        this.setAnnouncement(announcement);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
