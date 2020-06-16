package hu.inf.unideb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hu.inf.unideb.util.DB_connection;

public class LoginDAO {

    public static boolean validate(String user, String password) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = new DB_connection().getConnection();
            ps = con.prepareStatement("Select username, password from Customers where username = ? and password = ?");
            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {
            con.close();
        }
        return false;
    }
}
