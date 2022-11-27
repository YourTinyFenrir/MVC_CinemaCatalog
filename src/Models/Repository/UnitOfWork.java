package Models.Repository;

import Models.Entities.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UnitOfWork {

    private ActorRepository actorRepo;
    private FilmRepository filmRepo;
    private FilmDirectorRepository filmDirRepo;

    public UnitOfWork() {
        actorRepo = new ActorRepository();
        filmRepo = new FilmRepository();
        filmDirRepo = new FilmDirectorRepository();

        updateActorFilm();
        updateFilmDirector();
    }

    public Actor getActor(int key) {
        return actorRepo.get(key);
    }

    public Collection<Actor> getActors() {
        return actorRepo.getAll();
    }

    public void setActor(Actor actor) {

        this.actorRepo.set(actor);

        for (Film f : actor.getFilms()) {
            f.addActor(actor);
        }

        updateActorFilm(actor);
    }

    public Film getFilm(int key) {
        return filmRepo.get(key);
    }

    public Collection<Film> getFilms() {
        return filmRepo.getAll();
    }

    public void setFilm(Film film) {
        this.filmRepo.set(film);

        for (Actor actor : film.getActors()) {
            actor.addFilm(film);
        }

        updateActorFilm(film);

        this.filmDirRepo.get(film.getFilmDirector().getId()).addFilm(film);
    }

    public FilmDirector getFilmDirector(int key) {
        return filmDirRepo.get(key);
    }

    public Collection<FilmDirector> getFilmDirectors() {
        return filmDirRepo.getAll();
    }

    public void setFilmDirector(FilmDirector filmDirector) {
        this.filmDirRepo.set(filmDirector);

        for (Film f : filmDirector.getFilms()) {
            f.setFilmDirector(filmDirector);
        }
    }

    private Connection createConnection(String driver, String url) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }

    private void updateActorFilm(Actor actor) {

        try {

            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            String sql = "INSERT INTO Actor_film (ACTOR_ID, FILM_ID) " +
                    "VALUES (?, ?);";
            var stmt = conn.prepareStatement(sql);

            for (Film film : actor.getFilms()) {
                stmt.setInt(1, actor.getId());
                stmt.setInt(2, film.getId());

                stmt.executeUpdate();
                conn.commit();
            }

            stmt.close();
            conn.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    private void updateActorFilm(Film film) {

        try {

            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            String sql = "INSERT INTO Actor_film (ACTOR_ID, FILM_ID) " +
                    "VALUES (?, ?);";
            var stmt = conn.prepareStatement(sql);

            for (Actor actor : film.getActors()) {
                stmt.setInt(1, actor.getId());
                stmt.setInt(2, film.getId());

                stmt.executeUpdate();
                conn.commit();
            }

            stmt.close();
            conn.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation done successfully");
    }

    private void updateActorFilm() {

        try {

            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            System.out.println("Opened database successfully");

            var stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACTOR_FILM;" );

            while (rs.next()) {

                int actorId = rs.getInt("actor_id");
                int filmId = rs.getInt("film_id");

                Actor actor = actorRepo.get(actorId);
                Film film = filmRepo.get(filmId);

                actor.addFilm(film);
                film.addActor(actor);

            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation done successfully");
    }

    private void updateFilmDirector() {

        try {

            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            System.out.println("Opened database successfully");

            var stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM FILM;" );

            while (rs.next()) {

                int filmId = rs.getInt("id");
                int fdId = rs.getInt("film_director");

                Film film = filmRepo.get(filmId);
                FilmDirector fd = filmDirRepo.get(fdId);

                film.setFilmDirector(fd);
                fd.addFilm(film);

            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation done successfully");

    }

    public int actorLastKey() {
        return actorRepo.lastKey();
    }

    public int filmLastKey() {
        return filmRepo.lastKey();
    }

    public int filmDirectorLastKey() {
        return filmDirRepo.lastKey();
    }
}
