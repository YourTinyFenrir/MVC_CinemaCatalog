package Models.Repository;

import Models.Entities.Actor;
import Models.Entities.Film;
import Models.Entities.FilmDirector;
import Models.Interfaces.IRepository;

import java.sql.*;
import java.util.*;

public class FilmRepository implements IRepository<Film> {

    private HashMap<Integer, Film> films;

    public FilmRepository() {

        films = new HashMap<Integer, Film>();
        request_selectAll();

    }
    @Override
    public Connection createConnection(String driver, String url) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }

    @Override
    public Film get(int key) {
        return films.get(key);
    }

    @Override
    public void request_selectAll() {

        try {

            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            System.out.println("Opened database successfully");

            var stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM FILM;" );

            while (rs.next()) {
                int id = rs.getInt("id");
                int duration = rs.getInt("duration");
                String title = rs.getString("title");

                films.put(id, new Film(id, duration, title, null));
            }

            rs.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation done successfully");
    }

    @Override
    public void request_insert(Film film) {

        try {
            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            System.out.println("Opened database successfully");

            String sql = "INSERT INTO Film (ID, TITLE, DURATION, FILM_DIRECTOR) " +
                    "VALUES (?, ?, ?, ?);";
            var stmt = conn.prepareStatement(sql);

            stmt.setInt(1, film.getId());
            stmt.setString(2, film.getTitle());
            stmt.setInt(3, film.getDuration());

            // Режиссер не выбран (равен NULL) ???
            stmt.setInt(4, film.getFilmDirector().getId());

            stmt.executeUpdate();
            conn.commit();

            stmt.close();
            conn.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation done successfully");
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public void set(Film film) {

        films.put(film.getId(), film);
        request_insert(film);

    }

    public void setActor(Film film, Actor actor) {
        films.get(film.getId()).addActor(actor);
    }

    public void setFilmDirector(Film film, FilmDirector fd) {
        films.get(film.getId()).setFilmDirector(fd);
    }

    @Override
    public int lastKey() {

        List<Integer> keys = films.keySet().stream().toList();
        Collections.sort(keys);
        keys.get(keys.size());

        return keys.get(keys.size());
    }
}
