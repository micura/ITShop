package hu.inf.unideb;

import hu.inf.unideb.util.DB_connection;
import org.primefaces.PrimeFaces;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Named(value = "RegisterForm")
@SessionScoped
@ManagedBean
public class RegisterForm implements Serializable {
    private String password1;
    private String password2;
    private String username;
    private String lastname;
    private String firstname;
    private String email;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void saveData() throws NoSuchAlgorithmException {
        if(!(getPassword1().equals(getPassword2()))){
            showPasswordMismatch();
            return;
        }
        if(getPassword1().length()<8){
            showPasswordSmall();
            return;
        }
        if(!(getEmail().contains("@") &&  getEmail().contains("."))){
            showWrongEmail();
            return;
        }
        if(getFirstname().isEmpty() || getLastname().isEmpty() || getUsername().isEmpty()){
            usernfirstnlastnNull();
            return;
        }
        try {
            Connection con = new DB_connection().getConnection();
            System.out.println("trying to connect to db");
            con.setAutoCommit(false);
            if(con!=null){
                System.out.println("connecting to db");
                String sql =
                    "insert into customers(firstname, lastname, email, username, password) values(?,?,?,?,?)" ;

                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString (1, getFirstname());
                pst.setString (2, getLastname());
                pst.setString (3, getEmail());
                pst.setString (4, getUsername());
                pst.setString (5, toHexString(getSHA(getPassword1())));
                pst.execute();

                con.commit();
                con.close();
                succes();
            }
        } catch (Exception e) {
            usernOrEmailExists();
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void registrationSave() throws NoSuchAlgorithmException, SQLException, InterruptedException {
        saveData();


    }
    public RegisterForm() {
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public void showPasswordMismatch() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "A két megadott jelszó nem egyezik meg!", "Ellenőrizze, hogy a két megadott jelszó biztosan megegyezik-e.");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void showWrongEmail() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hibás e-mail cím!", "Ellenőrizze, hogy a megadott e-mail biztosan helyes-e.");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void showPasswordSmall() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "A megadott jelszó túl rövid!", "Ellenőrizze, hogy a megadott jelszó biztosan 8 karakter, vagy annál hosszab.");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void usernOrEmailExists(){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hiba történt!", "Rendszerünkben az ön felhasználóneve vagy e-mail címe már létezik. " +
                "Ha ön már regisztrált, használja a belépés funkciót.");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void succes(){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sikeres regisztráció!", "Az ön regisztrációja sikeres volt.");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void usernfirstnlastnNull(){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hiányzó adat(ok)!", "Az önáltal megadott vezetéknév vagy keresztnév vagy felhasználónév üres!");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
}
