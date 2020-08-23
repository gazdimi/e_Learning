package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

        String uname = request.getParameter("uname");           //get form inputs after submit
        String surname = request.getParameter("surname");
        String pass = request.getParameter("pass");
        String option = request.getParameter("option_type");
        String userid = option.concat(pass);

        try{
            salt = getSalt();
            securePassword = SecurePassword(pass, salt);
            UserFactory factory = new UserFactory();
            factory.UserIdentification(userid).register(userid, uname, surname, securePassword, salt);//User Factory Pattern
        } catch (Exception e){
            PrintWriter out = response.getWriter();	//Print html
            out.println("<!DOCTYPE html><html lang=\"en\">" +
                    "<head><title>Registration failed</title><style>input[type=button]{margin:50px 42% auto;font-size:10pt;font-weight:bold;}" +
                    "</style></head><body bgcolor = \"#f0f0f0\"><h1 align = \"center\">Registration failed</h1>\n" +
                    "<h3 align=\"center\">Please try again later.</h3>"+
                    "<input onclick=\"location.href='register.html'\" type=\"button\" value=\"GO_BACK_TO_REGISTER\">"+"</body></html>");
            return;
        }

        PrintWriter out = response.getWriter();
        String title = "Registration";
        out.println("<!DOCTYPE html><html lang=\"en\">" +
                "<head><title>" + title + "</title><style>input[type=button]{margin:50px 42% auto;font-size:10pt;font-weight:bold;}" +
                "</style></head><body bgcolor = \"#f0f0f0\"><h1 align = \"center\">" + title + "</h1>\n" +
                "<h3 align=\"center\">Successfull registration!!!</h3>"+
                "<input onclick=\"location.href='index.html'\" type=\"button\" value=\"Back to Login\">"+"</body></html>");

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
