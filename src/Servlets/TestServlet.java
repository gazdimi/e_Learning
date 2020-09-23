package Servlets;

import Classes.TestMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@WebServlet(name = "TestServlet",value ="/Test")
public class TestServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userid = (String)request.getSession().getAttribute("userid");        //get user id from session
        PrintWriter out = response.getWriter();
        try{
            if(request.getParameter("logout")!=null) {
                request.getSession().removeAttribute("userid");
                request.getSession().invalidate();
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //Student's progress "+progress+"%</div></div>" +   role=\"progressbar\" aria-valuenow=\""+progress+"\""+
                response.sendRedirect("login.html");
            }
            TestMapper test = new TestMapper();
            ResultSet res = test.get_test(S_WelcomeServlet.theory_id);
            String href = ""; String results = "";
            while (res.next()){
                href = res.getString("test_data");
                results = res.getString("responses");
            }
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy'T'HH:ss:mm");
            Date parsedDate = dateFormat.parse(dateFormat.format(date));
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            int duration = 1800000; //in milliseconds, 30 minutes
            Timestamp previous_timestamp = timestamp;
            timestamp.setTime(timestamp.getTime() + duration);

            ArrayList records = new ArrayList(); ArrayList<String> temp = new ArrayList<String>();
            URL csv = new URL(results);
            BufferedReader br = new BufferedReader(new InputStreamReader(csv.openStream()));
            String line;
            br.mark(0);
            while (true) {
                line = br.readLine();
                if(line == null) { br.reset();}
                String[] values = line.split(",");
                int size = values.length; int i = 0;
                while(i<size){
                    temp.add(values[i]);
                    i++;
                }
                if(previous_timestamp.before(Timestamp.valueOf(temp.get(0))) && timestamp.after(Timestamp.valueOf(temp.get(0)))) {
                    records.add(temp.get(0));
                    System.out.println(records.get(records.size()-1));
                    System.out.println(records.get(0)); //timestamp first element
                    break;
                }
                temp.clear();
            }
            br.close();
            System.out.println(records.get(records.size()-1)); //last element

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(href));
            }
            out.println("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Instructions for tests</title>\n" +
                    "<link href=\"./bootstrap/css/bootstrap.css\" rel=\"stylesheet\">" +
                    "<link href=\"./bootstrap/css/bootstrap-grid.css\" rel=\"stylesheet\">" +
                    "\"<link href=\"./bootstrap/css/bootstrap-reboot.css\" rel=\"stylesheet\">" +
                    "<script src=\"//code.jquery.com/jquery-1.11.0.min.js\"></script>" +
                    "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">" +
                    "<style>.sidenav {height: 100%; width: auto; position: fixed; z-index: 1; top: 0; left: 0; background-color: #042611;" +
                    "overflow-x: hidden; padding-top: 20px; } .sidenav a, #logout_form { padding: 6px 20px 6px 20px; text-decoration: none;" +
                    "font-size: 21px; color: #5f6e62; display: block; } .sidenav a:hover, #logout_form:hover { color: #edede4; } </style> </head>" +
                    "<body style=\"background: #FFEEEE;  /*for old browsers */ background: -webkit-linear-gradient(to right, #DDEFBB, #FFEEEE);  /* Chrome 10-25, Safari 5.1-6 */" +
                    "background: linear-gradient(to right, #DDEFBB, #FFEEEE); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */\">" +
                    "<div class=\"sidenav\"><div class=\"accordion\" id=\"accordion_menu\"><div id=\"heading_menu\">" +
                    "<a class=\"card-title\" href=\"#about\" data-toggle=\"collapse\" data-target=\"#collapse_menu\" aria-expanded=\"false\" aria-controls=\"collapse_menu\">Profile Info</a>" +
                    "</div><div id=\"collapse_menu\" class=\"collapse container text-white\" style=\"width: fit-content; background-color: #095426\" aria-labelledby=\"heading_menu\" data-parent=\"#accordion_menu\">" +
                    "<p class=\"card-text text-uppercase\"><span class=\"glyphicon glyphicon-user\"></span> " +
                    S_WelcomeServlet.first_name + " " + S_WelcomeServlet.last_name + "</p><p class=\"card-text\">Current Section: <p>  Multiplication of " + S_WelcomeServlet.theory_id +
                    "</p></p><p class=\"card-text\">Progress: " + S_WelcomeServlet.progress + "%</p></div></div><form method=\"post\" action=\"/Test\"><a href=\"contact.html\">Contact</a>" +
                    "<a href=\"about.html\">About</a><a><input type=\"submit\" value=\"Logout\" name=\"logout\" id=\"logout_form\" style=\"background:none; border-width:0px; padding-left: 0px;\"></a></form></div>" +
                    "<div class=\"progress\" style=\"margin-top: 2%; height: 32px;\"><div class=\"progress-bar progress-bar-success font-weight-bolder\" role=\"progressbar\" aria-valuenow=\""+S_WelcomeServlet.progress+"\"" +
                    "aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"+S_WelcomeServlet.progress+"%; font-size: 16px\">Student's progress "+S_WelcomeServlet.progress+"%</div></div>" +
                    "<div class=\"container\"><div class=\"row\"><div style=\"margin-left: 35%;\"><img src=\"img/test.png\" alt=\"Instructions\" width=\"400\" height=\"600\">\n" +
                    "</div></div></div><script src=\"./bootstrap/js/bootstrap.bundle.js\"></script><script src=\"./bootstrap/js/bootstrap.js\"></script>\n" +
                    "</body></html>");
        }catch (Exception e){System.out.println(e);}
    }
}
