package hu.inf.unideb.beans;

import hu.inf.unideb.primefaces.ProductInformation;
import hu.inf.unideb.util.DB_connection;

//import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


@Named
@SessionScoped
public class SearchBean implements Serializable {
    private String searchInput;

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String createURL(int id){
        return "product.xhtml?id="+id+"&faces-redirect=true";
    }

    public ArrayList<ProductInformation> search_data(String input){
        ArrayList arrayList = new ArrayList();
        try {
            Connection con = new DB_connection().getConnection();
            if(con!=null){
                input = input.toLowerCase();
                String sql = "select * from product_information" +
                        " where lower(product_name) like '%"+input+"%'" +
                        "or lower(product_description) like '%"+input+"%'";

                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while(rs.next()){
                    ProductInformation product = new ProductInformation();
                    product.setProduct_id(rs.getInt("product_id" ));
                    product.setProduct_name(rs.getString("product_name"));
                    product.setProduct_description(rs.getString("product_description"));
                    product.setProduct_status(rs.getString("product_status"));
                    product.setPrice(rs.getInt("price"));
                    product.setImage(rs.getString("image"));
                    product.setCategory_name(rs.getString("category_id"));


                    arrayList.add(product);
                }

                con.close();
                return arrayList;
            }
        } catch (Exception e) {
        }
        return null;
    }


    public SearchBean() {
    }

}
