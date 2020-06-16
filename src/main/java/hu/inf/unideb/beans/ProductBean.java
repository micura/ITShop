package hu.inf.unideb.beans;

import hu.inf.unideb.primefaces.Category;
import hu.inf.unideb.primefaces.ProductInformation;
import hu.inf.unideb.util.DB_connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Named(value="Product")
@SessionScoped
public class ProductBean implements Serializable {
    private final ArrayList<Integer> ordered_items = new ArrayList<>();

    @GetMapping("/product")
    @ResponseBody
    public String getProduct(@RequestParam int id) {
        return "ID: " + id;
    }

    public ArrayList<ProductInformation> getProductData(int productId){
        ArrayList arrayListForProduct = new ArrayList();
        try {
            Connection con = new DB_connection().getConnection();
            if(con!=null){

                PreparedStatement ps = con.prepareStatement("select * from product_information where product_id = ?");
                ps.setInt(1, productId);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    ProductInformation product = new ProductInformation();
                    product.setProduct_id(rs.getInt("product_id" ));
                    product.setProduct_name(rs.getString("product_name"));
                    product.setProduct_description(rs.getString("product_description"));
                    product.setProduct_status(rs.getString("product_status"));
                    product.setPrice(rs.getInt("price"));
                    product.setImage(rs.getString("image"));

                    arrayListForProduct.add(product);
                }

                con.close();
                return arrayListForProduct;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getCategoryName(int producdId){
        try {
            Connection con = new DB_connection().getConnection();
            String category_name = null;

            if(con!=null){
                PreparedStatement ps = con.prepareStatement("select category_name from category d1\n" +
                        "JOIN product_information d2\n" +
                        "on d1.category_id = d2.category_id\n" +
                        "where d2.product_id = ?;");
                ps.setInt(1, producdId);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Category category = new Category();
                    category.setCategory_name(rs.getString("category_name"));
                    category_name = category.getCategory_name();
                }

                return category_name;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public ArrayList<ProductInformation> getProductsByCategoryid(int categoryId){
        ArrayList arrayListForProduct = new ArrayList();
        try {
            Connection con = new DB_connection().getConnection();
            if(con!=null){

                PreparedStatement ps = con.prepareStatement("select * from product_information where category_id = ?;");
                ps.setInt(1, categoryId);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    ProductInformation product = new ProductInformation();
                    product.setProduct_id(rs.getInt("product_id" ));
                    product.setProduct_name(rs.getString("product_name"));
                    product.setProduct_description(rs.getString("product_description"));
                    product.setProduct_status(rs.getString("product_status"));
                    product.setPrice(rs.getInt("price"));
                    product.setImage(rs.getString("image"));

                    arrayListForProduct.add(product);
                }

                con.close();
                return arrayListForProduct;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void addToCart( ) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        String id = request.getParameter("id");
        ordered_items.add(Integer.parseInt(id));
        System.out.println(ordered_items);
    }

    public ArrayList<Integer> getOrdered_items() {
        ArrayList products = new ArrayList();
        try {
            Connection con = new DB_connection().getConnection();

            if (con != null) {
                for (int counter = 0; counter < ordered_items.size(); counter++) {

                    PreparedStatement ps = con.prepareStatement("select * from product_information where product_id = ?;");
                    ps.setInt(1, ordered_items.get(counter));
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        ProductInformation product = new ProductInformation();
                        product.setProduct_id(rs.getInt("product_id"));
                        product.setProduct_name(rs.getString("product_name"));
                        product.setProduct_description(rs.getString("product_description"));
                        product.setProduct_status(rs.getString("product_status"));
                        product.setPrice(rs.getInt("price"));
                        product.setImage(rs.getString("image"));
                        products.add(product);
                    }
                }
                con.close();
                return products;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductBean() {
    }
}