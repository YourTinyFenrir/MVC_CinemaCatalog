package Controllers.Interfaces;

import Models.Entities.*;

import java.util.Collection;

public interface ICatalogController {

    void setActor(Actor actor);
    void setFilm(Film film);
    void setFilmDirector(FilmDirector fd);

    Actor getActor(int key);
    Film getFilm(int key);
    FilmDirector getFilmDirector(int key);

    Collection<Actor> getActors();
    Collection<Film> getFilms();
    Collection<FilmDirector> getFilmDirectors();

}
