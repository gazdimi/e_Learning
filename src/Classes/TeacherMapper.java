package Classes;

import Servlets.RegisterServlet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements User{

    //Inserts teacher information to database
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

    public String login(String username,String password) throws SQLException {
        try{
            Dbconnector con = new Dbconnector();
            PreparedStatement s = con.connect().prepareStatement("SELECT teacher_id, password,first_name, salt FROM teachers where first_name = ?;");
            s.setString(1, username);
            ResultSet Rs1 = s.executeQuery();
            if(Rs1.next()) {
                byte[] salt = Rs1.getBytes("salt");
                String securePassword = RegisterServlet.SecurePassword(password,salt);
                if(username.equals(Rs1.getString("first_name"))&&securePassword.equals(Rs1.getString("password"))) {
                    String id =  Rs1.getString("teacher_id");
                    con.disconnect();
                    return id;
                }
            }else{
                return "";
            }
        }catch(Exception e){
            throw new SQLException("Incorrect credentials");
        }
        return null;
    }

    //return teacher's information
    public ResultSet get_info(String userid) throws SQLException{
        try {
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT first_name, last_name FROM teachers WHERE teachers.teacher_id = ? ;");
            st.setString(1, userid);
            ResultSet rs = st.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new SQLException("Could not get teacher's data.");

        }
    }

    //Get student's info to display in teacher's homepage
    public ResultSet get_students() throws SQLException{
        try {
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT first_name, last_name, theory_id, progress FROM students ");
            ResultSet rs = st.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new SQLException("Could not get student's data.");

        }
    }
}
