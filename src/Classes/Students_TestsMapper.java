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

    //Return true or false if all tests have been completed successfully
    public boolean e_learning_done(String student_id) throws SQLException{
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT EXISTS (SELECT test_id FROM students_tests WHERE students_tests.student_id = ? \n" +
                    "AND students_tests.weakness = false AND students_tests.test_id = '12');");
            st.setString(1, student_id);
            ResultSet rs = st.executeQuery();
            rs.next();
            return  rs.getBoolean(1);
        }catch (Exception e){
            throw new SQLException("Could not check if e_learning was done.");
        }
    }

    public int get_number_of_passed_tests(String student_id) throws SQLException{
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT count(test_id) FROM students_tests WHERE students_tests.student_id = ? and students_tests.weakness = false;");
            st.setString(1, student_id);
            ResultSet rs = st.executeQuery();
            rs.next();
            return  rs.getInt(1);
        }catch (Exception e){
            throw new SQLException("Could not get number of passed tests.");
        }
    }

    public int get_next_repeated_test(String student_id) throws SQLException{
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT MAX(test_id) FROM students_tests WHERE students_tests.student_id = ? and students_tests.weakness = false;");
            st.setString(1, student_id);
            ResultSet rs = st.executeQuery();
            rs.next();
            int temp = rs.getInt(1);
            if(temp < 12) { temp++; }
            return temp;
        }catch (Exception e){
            throw new SQLException("Could not get next repeated test.");
        }
    }
}
