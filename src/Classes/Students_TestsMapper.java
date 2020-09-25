package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Students_TestsMapper {

    //Insert success for specific test and student
    public void add_student_test(String student_id, int test_id,String st_results, Boolean weakness) throws SQLException {
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("INSERT INTO students_tests(student_id,test_id,st_results,weakness) VALUES(?,?,?,?);");
            st.setString(1, student_id);
            st.setInt(2, test_id);
            st.setString(3, st_results);
            st.setBoolean(4, weakness);
            st.executeUpdate();
        }
        catch (Exception e){
            throw new SQLException("Could not insert student_test association.");
        }
    }

    public Boolean check_for_student_test(String student_id, int test_id) throws SQLException{
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT EXISTS (SELECT * FROM students_tests where students_tests.student_id = ? and students_tests.test_id = ?);");
            st.setString(1, student_id);
            st.setInt(2, test_id);
            ResultSet rs = st.executeQuery();
            rs.next();
            return  rs.getBoolean(1);
        }catch (Exception e){
            throw new SQLException("Could not check for student_test association.");
        }
    }

    //Update test-student
    public void update_student_test(String student_id, int test_id, String st_results, Boolean weakness) throws SQLException {
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("UPDATE students_tests SET st_results = ?, weakness = ? WHERE students_tests.student_id=? AND students_tests.test_id=?;");
            st.setString(1, st_results);
            st.setBoolean(2, weakness);
            st.setString(3, student_id);
            st.setInt(4, test_id);
            st.executeUpdate();
        }
        catch (Exception e){
            //throw new SQLException("Could not update student_test association.");
            System.out.println(e);
        }
    }
}
