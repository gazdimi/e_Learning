package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoogleFormsMapper {

    public String get_google_form_test(int theory_id) throws SQLException {
        try {
            Dbconnector connector = new Dbconnector();
            PreparedStatement st = connector.connect().prepareStatement("SELECT google_form FROM google_forms_tests WHERE google_forms_tests.number_id = ? ;");
            st.setInt(1, theory_id);
            ResultSet rs = st.executeQuery();
            rs.next();
            return  rs.getString(1);
        } catch (Exception e) {
            throw new SQLException("Could not get google form.");

        }
    }
}
