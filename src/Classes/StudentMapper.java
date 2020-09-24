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
    public String login(String username,String password) throws SQLException {
        try{
            Dbconnector con = new Dbconnector();
            PreparedStatement sm = con.connect().prepareStatement("SELECT student_id, first_name, password, salt FROM students where first_name = ?;");
            sm.setString(1, username);
            ResultSet Rs1 = sm.executeQuery();
            if(Rs1.next()) {
                byte[] salt = Rs1.getBytes("salt");
                String securePassword = RegisterServlet.SecurePassword(password,salt);
                if(username.equals(Rs1.getString("first_name"))&&securePassword.equals(Rs1.getString("password"))) {
                    String id = Rs1.getString("student_id");
                    con.disconnect();
                    return id;
                }
            }else{
                return null;
            }
        }catch(Exception e){
            throw new SQLException("Incorrect credentials");
        }
        return null;
    }

    //return student's information
    public ResultSet get_info(String userid) throws SQLException{
        try {
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT first_name, last_name, theory_id, progress FROM students WHERE students.student_id = ? ;");
            st.setString(1, userid);
            ResultSet rs = st.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new SQLException("Could not get student's data.");

        }
    }

    //Update student's info after successful test
    public void update_info(String student_id, String theory_id) throws SQLException {
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("UPDATE students set theory_id=? where students.student_id=?");
            st.setString(1, theory_id);
            st.setString(2, student_id);
            st.executeUpdate();
        }
        catch (Exception e){
            throw new SQLException("Could not update student's info");
        }
    }
}
