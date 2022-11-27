package Models.Repository;

import Models.Entities.Actor;
import Models.Entities.FilmDirector;
import Models.Interfaces.IRepository;

import java.sql.*;
import java.util.*;

public class FilmDirectorRepository implements IRepository<FilmDirector> {

    private HashMap<Integer, FilmDirector> filmDirectors;

    public FilmDirectorRepository() {

        filmDirectors = new HashMap<Integer, FilmDirector>();
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
    public void request_selectAll() {

        try {

            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            System.out.println("Opened database successfully");

            var stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM FILM_DIRECTOR;" );

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                int age  = rs.getInt("age");
                String nationality = rs.getString("nationality");

                filmDirectors.put(id, new FilmDirector(id, name, surname, patronymic, age, nationality));
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
    public void request_insert(FilmDirector filmDirector) {

        try {

            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            System.out.println("Opened database successfully");

            String sql = "INSERT INTO Film_director (ID, NAME, SURNAME, PATRONYMIC, AGE, NATIONALITY) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            var stmt = conn.prepareStatement(sql);

            stmt.setInt(1, filmDirector.getId());
            stmt.setString(2, filmDirector.getName());
            stmt.setString(3, filmDirector.getSurname());
            stmt.setString(4, filmDirector.getPatronymic());
            stmt.setInt(5, filmDirector.getAge());
            stmt.setString(6, filmDirector.getNationality());

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
    public Collection<FilmDirector> getAll() {
        return filmDirectors.values();
    }

    @Override
    public FilmDirector get(int key) {
        return filmDirectors.get(key);
    }

    @Override
    public void set(FilmDirector filmDirector) {

        filmDirectors.put(filmDirector.getId(), filmDirector);
        request_insert(filmDirector);

    }

    @Override
    public int lastKey() {

        List<Integer> keys = filmDirectors.keySet().stream().toList();
        Collections.sort(keys);
        keys.get(keys.size());

        return keys.get(keys.size());
    }

}
