package Classes;

import Servlets.RegisterServlet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements User{

    //Inserts student information to database
    public void register(String id,String name,String surname,String password,byte[] salt) throws SQLException {
        try{
            Dbconnector con = new Dbconnector();
            PreparedStatement st = con.connect().prepareStatement("INSERT INTO teachers (teacher_id,password,first_name,last_name,salt) VALUES(?,?,?,?,?);");
            st.setString(1, id);
            st.setString(2, password);
            st.setString(3, name);
            st.setString(4, surname);
            st.setBytes(5, salt);
            st.executeUpdate();
            st.close();
            con.disconnect();
        }catch(Exception e){
            throw new SQLException("Teacher could not register");
        }
    }

    public boolean login(String username,String password) throws SQLException {
        try{
            Dbconnector con = new Dbconnector();
            PreparedStatement s = con.connect().prepareStatement("SELECT teacher_id, password,first_name, salt FROM teachers where first_name = ?;");
            s.setString(1, username);
            ResultSet Rs1 = s.executeQuery();
            if(Rs1.next()) {
                byte[] salt = Rs1.getBytes("salt");
                String securePassword = RegisterServlet.SecurePassword(password,salt);
                if(username.equals(Rs1.getString("first_name"))&&securePassword.equals(Rs1.getString("password"))) {
                    con.disconnect();
                    return true;
                }
            }else{
                return false;
            }
        }catch(Exception e){
            throw new SQLException("Incorrect credentials");
        }
        return false;
    }
}
