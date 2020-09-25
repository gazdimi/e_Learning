package Servlets;

import Classes.GoogleFormsMapper;
import Classes.TeacherMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(name = "T_WelcomeServlet",value ="/T_Welcome")
public class T_WelcomeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userid = (String)request.getSession().getAttribute("userid");        //get user id from session
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Teacher Homepage</title>\n" +
                "<link href=\"./bootstrap/css/bootstrap.css\" rel=\"stylesheet\">\n" +
                "<link href=\"./bootstrap/css/bootstrap-grid.css\" rel=\"stylesheet\">\n" +
                "<link href=\"./bootstrap/css/bootstrap-reboot.css\" rel=\"stylesheet\">\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.0.min.js\"></script>\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
                "<style>.sidenav { height: 100%; width: auto; position: fixed; z-index: 1; top: 0; left: 0; background-color: #042611;\n" +
                "overflow-x: hidden; padding-top: 20px; } .sidenav a, #logout_form { padding: 6px 20px 6px 20px;\n" +
                "text-decoration: none; font-size: 21px; color: #5f6e62; display: block; } .sidenav a:hover, #logout_form:hover {\n" +
                "color: #edede4; }</style></head><body style=\"background: #FFEEEE;  /*for old browsers */ background: -webkit-linear-gradient(to right, #DDEFBB, #FFEEEE);  /* Chrome 10-25, Safari 5.1-6 */\n" +
                " background: linear-gradient(to right, #DDEFBB, #FFEEEE); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */\">\n" +
                "<div class=\"sidenav\"><div class=\"accordion\" id=\"accordion_menu\"><div id=\"heading_menu\">\n" +
                "<a class=\"card-title\" href=\"#about\" data-toggle=\"collapse\" data-target=\"#collapse_menu\" aria-expanded=\"false\" aria-controls=\"collapse_menu\">Profile Info</a>\n" +
                "</div><div id=\"collapse_menu\" class=\"collapse container text-white\" style=\"width: fit-content; background-color: #095426\" aria-labelledby=\"heading_menu\" data-parent=\"#accordion_menu\">\n" +
                "<p class=\"card-text text-uppercase\"><span class=\"glyphicon glyphicon-user\"></span>");
        try{
            if(request.getParameter("logout")!=null) {
                request.getSession().removeAttribute("userid");
                request.getSession().invalidate();
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.sendRedirect("login.html");
            }
            TeacherMapper t = new TeacherMapper();
            ResultSet rs = t.get_info(userid);
            String first_name = ""; String last_name = "";
            while (rs.next()){
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");
            }
            out.println( first_name + " " + last_name + "</p></div></div><form method=\"post\" action=\"/T_Welcome\">\n" +
                    "<a href=\"contact.html\">Contact</a><a href=\"about.html\">About</a>\n" +
                    "<a><input type=\"submit\" value=\"Logout\" name=\"logout\" id=\"logout_form\" style=\"background:none; border-width:0px; padding-left: 0px;\"></a>\n" +
                    "</form></div><div class=\"container\" style=\"margin-top: 30px; margin-left: 20%\">\n" +
                    "<div class=\"row\"><div class=\"container d-flex\" style=\"margin-left: 20%\">\n" +
                    "<div class=\"shadow p-3 mb-5 bg-white rounded\"><div class=\"card text-center \" style=\"width: 45rem;\">\n" +
                    "<div class=\"card-body\"><h5 class=\"card-title\">Information abou students' progress</h5>\n" +
                    "<h6 class=\"card-subtitle mb-2 text-muted\">Detailed data</h6><div class = \"col\"><form>\n" +
                    "<ul class=\"list-group list-group-flush\"><table class=\"table\"><thead><tr><th scope=\"col\">Name</th><th scope=\"col\">Surname</th>" +
                    "<th scope=\"col\">Theory identifier</th><th scope=\"col\">Progress</th></tr></thead><tbody>");
            ResultSet students_info = t.get_students();
            while (students_info.next()){
                out.println("<tr><td>"+students_info.getString("first_name")+"</td><td>"+students_info.getString("last_name")+"</td><td>"+students_info.getInt("theory_id")+"</td><td>"+students_info.getInt("progress")+"</td></tr>");
            }
            out.println("</tbody></table><br><h5>Click any of the links below to see students' progress in the available google forms</h5>");
            GoogleFormsMapper forms = new GoogleFormsMapper();
            ResultSet links = forms.get_google_form_results();
            while (links.next()){
                out.println("<a href=\""+links.getString("st_progress")+"\">Multiplication of "+links.getInt("number_id")+" - Test "+links.getInt("number_id")+"</a>");
            }
            out.println("</ul></form><br></div></div></div></div></div></div></div><script src=\"./bootstrap/js/bootstrap.bundle.js\"></script>\n" +
                    "<script src=\"./bootstrap/js/bootstrap.js\"></script></body></html>");
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
