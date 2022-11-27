package Models.Repository;

import Models.Entities.Actor;
import Models.Entities.Film;
import Models.Interfaces.IRepository;

import java.sql.*;
import java.util.*;

public class ActorRepository implements IRepository<Actor> {

    private HashMap<Integer,Actor> actors;

    public ActorRepository() {

        actors = new HashMap<Integer, Actor>();
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
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACTOR;" );

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                int age  = rs.getInt("age");
                String nationality = rs.getString("nationality");

                actors.put(id, new Actor(id, name, surname, patronymic, age, nationality));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation done successfully");
    }

    @Override
    public void request_insert(Actor actor) {

        try {
            var conn = createConnection("org.sqlite.JDBC",
                    "jdbc:sqlite:.\\sqLite\\CinemaCatalog.db");

            System.out.println("Opened database successfully");

            String sql = "INSERT INTO Actor (ID, NAME, SURNAME, PATRONYMIC, AGE, NATIONALITY) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            var stmt = conn.prepareStatement(sql);

            stmt.setInt(1, actor.getId());
            stmt.setString(2, actor.getName());
            stmt.setString(3, actor.getSurname());
            stmt.setString(4, actor.getPatronymic());
            stmt.setInt(5, actor.getAge());
            stmt.setString(6, actor.getNationality());

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
    public Actor get(int key) {
        return actors.get(key);
    }

    @Override
    public Collection<Actor> getAll() {
        return actors.values();
    }

    @Override
    public void set(Actor actor) {

        actors.put(actor.getId(), actor);
        request_insert(actor);

    }

    public void setFilm(Actor actor, Film film) {
        actors.get(actor.getId()).addFilm(film);
    }

    @Override
    public int lastKey() {

//        var keys = actors.keySet();
//        Collections.sort(keys);
//        keys.get(keys.size());
//
//        return keys.get(keys.size());

        return 0;
    }

}
