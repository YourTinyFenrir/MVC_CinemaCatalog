package Models.Entities;

import java.util.ArrayList;

public class Actor extends Human {

    private ArrayList<Film> films;

    public Actor(int id, String name, String surname, String patronymic, int age, String nationality) {
        super(id, name, surname, patronymic, age, nationality);
        films = new ArrayList<Film>();
    }
    public void addFilm(Film film) {
        films.add(film);
    }

    public ArrayList<Film> getFilms() { return films; }

}
