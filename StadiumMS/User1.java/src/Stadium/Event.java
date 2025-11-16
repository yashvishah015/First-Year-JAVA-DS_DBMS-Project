package Stadium;

public class Event {
    private int id;
    private String name;
    private String event_date;
    private String location;

    public Event(int id, String name, String event_date, String location) {
        this.id = id;
        this.name = name;
        this.event_date = event_date;
        this.location = location;
    }

   
    public String toString() {
        return "Event{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", event_date='" + event_date + '\'' +
               ", location='" + location + '\'' +
               '}';
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

    public String getevent_date() {
        return event_date;
    }

    public void setevent_date(String event_date) {
        this.event_date =event_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
