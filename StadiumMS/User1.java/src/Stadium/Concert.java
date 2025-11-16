package Stadium;

public class Concert {
    private int id;
    private int eventId;
    private String artist;
    private String genre;

    public Concert(int id, int eventId, String artist, String genre) {
        this.id = id;
        this.eventId = eventId;
        this.artist = artist;
        this.genre = genre;
    }

    
    public String toString() {
        return "Concert{" +
               "id=" + id +
               ", eventId=" + eventId +
               ", artist='" + artist + '\'' +
               ", genre='" + genre + '\'' +
               '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
