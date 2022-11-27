package Models.Entities;

import java.util.Date;

public abstract class CinematicWork {

    int id;
    int duration;
    String title;

    public CinematicWork() {
        duration = -1;
    }

    public CinematicWork(int id, int duration, String title) {
        this.id = id;
        this.duration = duration;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title;
    }
}
