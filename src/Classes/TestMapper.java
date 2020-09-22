package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMapper {

    //Return test for specific theory_id
    public String get_test(String theory_id) throws  SQLException{
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT test_data FROM tests where tests.test_id = ?;");
            st.setString(1, theory_id);
            ResultSet rs = st.executeQuery();
            rs.next();
            return  rs.getString(1);
        }
        catch (Exception e){
            throw new SQLException("Could not load test data.");
        }
    }
}
