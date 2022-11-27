package Models.Interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public interface IRepository<T> {
    Connection createConnection(String driver, String url)  throws ClassNotFoundException, SQLException;
    void request_selectAll();
    void request_insert(T t);
    Collection<T> getAll();
    T get(int key);
    void set(T t);
    int lastKey();

}
