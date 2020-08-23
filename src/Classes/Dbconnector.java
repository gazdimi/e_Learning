package Classes;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Dbconnector {

    Connection con;
    //Returns a connection to database
    public Connection connect() throws SQLException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres"); //Datasourse location
            con = ds.getConnection(); //Connection to database
            return con;
        } catch (Exception e) {
            throw new SQLException("Can't connect to database");
        }
    }
    //Disconnect from database
    public void disconnect() throws Exception{
        try {
            con.close();
        }catch(Exception e) {
            throw new Exception("No existing connection to database");
        }
    }

}
