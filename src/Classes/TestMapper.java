package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMapper {

    //Return test for specific theory_id
    public ResultSet get_test(String theory_id) throws  SQLException{
        try{
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT * FROM tests where tests.test_id = ?;");
            st.setString(1, theory_id);
            ResultSet rs = st.executeQuery();
            return  rs;
        }
        catch (Exception e){
            throw new SQLException("Could not load test data.");
        }
    }
}
