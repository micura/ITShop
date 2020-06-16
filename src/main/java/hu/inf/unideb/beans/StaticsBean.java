package hu.inf.unideb.beans;

import hu.inf.unideb.primefaces.Category;
import hu.inf.unideb.util.DB_connection;
import org.apache.jasper.compiler.JspUtil;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Named(value="Static")
public class StaticsBean {
    public int getNumberOfCategory(String category_name){
        try {
            Connection con = new DB_connection().getConnection();
            int numberOfCategory = 0;

            if(con!=null){
                PreparedStatement ps = con.prepareStatement("Select count(*) from product_information d1\n" +
                        "JOIN category d2\n" +
                        "on d1.category_id = d2.category_id\n" +
                        "where d2.category_name =?;");

                ps.setString(1, category_name);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    numberOfCategory = rs.getInt("count(*)");
                }

                con.close();
                return numberOfCategory;
            }
        } catch (Exception e) {

        }
        return 0;
    }
}
