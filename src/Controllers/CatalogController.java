package Controllers;

import Controllers.Interfaces.ICatalogController;
import Models.Entities.Actor;
import Models.Entities.Film;
import Models.Entities.FilmDirector;
import Models.Repository.UnitOfWork;

import Views.ActorsView;
import Views.FilmDirectorsView;
import Views.FilmsView;

import java.util.Collection;

public class CatalogController implements ICatalogController {

    private UnitOfWork uow;

    public CatalogController() {

        uow = new UnitOfWork();

        //FilmsView fv = new FilmsView(getFilms(), getFilmDirectors());
        //ActorsView av = new ActorsView(getActors());
        FilmDirectorsView fv = new FilmDirectorsView(getFilmDirectors());

    }

    @Override
    public void setActor(Actor actor) {
        uow.setActor(actor);
    }

    @Override
    public void setFilm(Film film) {
        uow.setFilm(film);
    }

    @Override
    public void setFilmDirector(FilmDirector fd) {
        uow.setFilmDirector(fd);
    }

    @Override
    public Actor getActor(int key) {
        return uow.getActor(key);
    }

    @Override
    public Film getFilm(int key) {
        return uow.getFilm(key);
    }

    @Override
    public FilmDirector getFilmDirector(int key) {
        return uow.getFilmDirector(key);
    }

    @Override
    public Collection<Actor> getActors() {
        return uow.getActors();
    }

    @Override
    public Collection<Film> getFilms() {
        return uow.getFilms();
    }

    @Override
    public Collection<FilmDirector> getFilmDirectors() {
        return uow.getFilmDirectors();
    }
}
