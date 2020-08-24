package Classes;

import Servlets.RegisterServlet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements User{

    //Inserts student information to database
    public void register(String id,String name,String surname,String password,byte[] salt) throws SQLException {
        try{
            Dbconnector con = new Dbconnector();
            PreparedStatement st = con.connect().prepareStatement("INSERT INTO students (student_id, teacher_id, theory_id,password,first_name,last_name,salt,progress) VALUES(?,?,?,?,?,?,?,?);");
            st.setString(1, id);
            st.setString(2, null);
            st.setString(3, "1");
            st.setString(4, password);
            st.setString(5, name);
            st.setString(6, surname);
            st.setBytes(7, salt);
            st.setDouble(8, 0.0);
            st.executeUpdate();
            st.close();
            con.disconnect();
        }catch(Exception e){
            throw new SQLException("Student could not register");
        }
    }

    //Checks user information
    public boolean login(String username,String password) throws SQLException {
        try{
            Dbconnector con = new Dbconnector();
            PreparedStatement sm = con.connect().prepareStatement("SELECT student_id, first_name, password, salt FROM students where first_name = ?;");
            sm.setString(1, username);
            ResultSet Rs1 = sm.executeQuery();
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
