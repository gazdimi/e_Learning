package Classes;

import java.sql.SQLException;

public interface User {
    public void register(String id,String name,String surname,String password,byte[] salt) throws SQLException;
    public String login(String username,String password) throws SQLException;
}
