package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.*;
import Classes.UserFactory;

@WebServlet(name = "RegisterServlet",value ="/Register")
public class RegisterServlet extends HttpServlet {

    private static String generatedPassword = null;
    private String securePassword = null;
    private static byte[] salt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uname = request.getParameter("uname").toLowerCase();           //get form inputs after submit
        String surname = request.getParameter("surname").toLowerCase();
        String pass = request.getParameter("pass").toLowerCase();
        String option = request.getParameter("option_type");
        String userid = option.concat(pass);

        try{
            salt = getSalt();
            securePassword = SecurePassword(pass, salt);
            UserFactory factory = new UserFactory();
            factory.UserIdentification(userid).register(userid, uname, surname, securePassword, salt);//User Factory Pattern
        } catch (Exception e){
            response.sendRedirect("failure.html");
            return;
        }
        response.sendRedirect("success.html");
    }

    public static String SecurePassword(String pass,byte[] salt) {

        try {
            //Using sha-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //add salt
            md.update(salt);
            //md bytes to hash
            byte[] hash = md.digest(pass.getBytes());
            //Decimal to hex
            StringBuffer sb = new StringBuffer();
            for(int i=0; i< hash.length ;i++)
            {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            //hex format
            generatedPassword = sb.toString(); //final hash & salt pass word
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;

    }
    //generates a  salt
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //64 bit salt array
        byte[] salt2 = new byte[8];
        //random salt to fill the array
        sr.nextBytes(salt2);
        return salt2;

    }

}
