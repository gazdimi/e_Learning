package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TheoryMapper {

    //Return theory sections that has been unlocked by the student
    public ResultSet get_theory_(String theory_id) throws SQLException {
        try {
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT * FROM theory WHERE  theory_id <= ?");
            st.setString(1, theory_id);
            ResultSet rs = st.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new SQLException("Could not access theory data.");

        }
    }

}
