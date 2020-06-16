package hu.inf.unideb.primefaces;

public class ProductInformation {
    private int product_id;
    private String product_name;
    private String product_description;
    private String product_status;
    private int price;
    private String image;
    private String category_name;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_status() {
        return product_status;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductInformation() {
    }
}
