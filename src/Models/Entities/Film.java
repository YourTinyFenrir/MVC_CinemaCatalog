package Models.Entities;

import Models.Entities.CinematicWork;

import java.util.ArrayList;

public class Film extends CinematicWork {

    private FilmDirector filmDirector;
    private ArrayList<Actor> actors;

    public Film(int id, int duration, String title, FilmDirector filmDirector) {
        super(id, duration, title);
        this.filmDirector = filmDirector;
        actors = new ArrayList<Actor>();
    }

    public FilmDirector getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(FilmDirector filmDirector) {
        this.filmDirector = filmDirector;
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public ArrayList<Actor> getActors() { return actors; }

}
