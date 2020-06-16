package hu.inf.unideb.beans;

import hu.inf.unideb.primefaces.Category;
import hu.inf.unideb.util.DB_connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Named(value="Category")
public class CategoryBean {
    private Connection con;

    @GetMapping("/category")
    @ResponseBody
    public String getCategory(@RequestParam int id) {
        return "ID: " + id;
    }

    public ArrayList<String> getCategoriesName(){
        ArrayList<String> categoryNames = new ArrayList<>();
        String category_name = null;

        try {
            Connection con = new DB_connection().getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement("select category_name from category");
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Category category = new Category();
                    category.setCategory_name(rs.getString("category_name"));
                    category_name = category.getCategory_name();
                    category_name = category_name.substring(0, 1).toUpperCase() + category_name.substring(1).toLowerCase();

                    categoryNames.add(category_name);
                }
                con.close();
                return categoryNames;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String  getCategoriesNameById(int categoryId){
        String category_name = null;

        try {
            Connection con = new DB_connection().getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement("select category_name from category WHERE category_id = ?");
                ps.setInt(1, categoryId);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Category category = new Category();
                    category.setCategory_name(rs.getString("category_name"));
                    category_name = category.getCategory_name();
                    category_name = category_name.substring(0, 1).toUpperCase() + category_name.substring(1).toLowerCase();
                }
                con.close();
                return category_name;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public int getCategoriesIdByName(String categoryName){
        int categoryId = 0;
        categoryName = categoryName.toLowerCase();

        try {
            Connection con = new DB_connection().getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement("select category_id from category WHERE category_name = ?");
                ps.setString(1, categoryName);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Category category = new Category();
                    category.setCategory_id(rs.getInt("category_id"));
                    categoryId = category.getCategory_id();

                }
                con.close();
                return categoryId;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public CategoryBean() {
    }
}
