package Servlets;

import Classes.StudentMapper;
import Classes.TheoryMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(name = "S_WelcomeServlet",value ="/S_Welcome")
public class S_WelcomeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userid = (String)request.getSession().getAttribute("userid");        //get user id from session
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Student Homepage</title>\n" +
                "<link href=\"./bootstrap/css/bootstrap.css\" rel=\"stylesheet\">\n" +
                "<link href=\"./bootstrap/css/bootstrap-grid.css\" rel=\"stylesheet\">\n" +
                "<link href=\"./bootstrap/css/bootstrap-reboot.css\" rel=\"stylesheet\">\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.0.min.js\"></script>\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
                "<style>.sidenav {height: 100%; width: auto; position: fixed; z-index: 1; top: 0; left: 0; background-color: #042611;\n" +
                "overflow-x: hidden; padding-top: 20px; } .sidenav a, #logout_form { padding: 6px 20px 6px 20px; text-decoration: none;\n" +
                "font-size: 21px; color: #5f6e62; display: block; } .sidenav a:hover, #logout_form:hover { color: #edede4; } </style> </head>\n" +
                "<body style=\"background: #FFEEEE;  /*for old browsers */ background: -webkit-linear-gradient(to right, #DDEFBB, #FFEEEE);  /* Chrome 10-25, Safari 5.1-6 */\n" +
                "background: linear-gradient(to right, #DDEFBB, #FFEEEE); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */\">\n" +
                "<div class=\"sidenav\"><div class=\"accordion\" id=\"accordion_menu\"><div id=\"heading_menu\">\n" +
                "<a class=\"card-title\" href=\"#about\" data-toggle=\"collapse\" data-target=\"#collapse_menu\" aria-expanded=\"false\" aria-controls=\"collapse_menu\">Profile Info</a>\n" +
                "</div><div id=\"collapse_menu\" class=\"collapse container text-white\" style=\"width: fit-content; background-color: #095426\" aria-labelledby=\"heading_menu\" data-parent=\"#accordion_menu\">\n" +
                "<p class=\"card-text text-uppercase\"><span class=\"glyphicon glyphicon-user\"></span> ");
        try{
            if(request.getParameter("logout")!=null) {
                request.getSession().removeAttribute("userid");
                request.getSession().invalidate();
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.sendRedirect("login.html");
            }
            StudentMapper s = new StudentMapper();
            ResultSet info = s.get_info(userid);
            String first_name = ""; String last_name = ""; String theory_id = ""; int progress = 0;
            while (info.next()){
                first_name = info.getString("first_name");
                last_name = info.getString("last_name");
                theory_id = info.getString("theory_id");
                progress = (int) ((info.getDouble("progress")*100)/10);
            }
            out.println( first_name + " " + last_name + "</p><p class=\"card-text\">Current Section: <p>  Multiplication of " + theory_id +
                    "</p></p><p class=\"card-text\">Progress: " + progress + "%</p></div></div><form method=\"post\" action=\"/S_Welcome\"><a href=\"contact.html\">Contact</a>" +
                    "<a href=\"about.html\">About</a><a><input type=\"submit\" value=\"Logout\" name=\"logout\" id=\"logout_form\" style=\"background:none; border-width:0px; padding-left: 0px;\"></a></form></div>" +
                    "<div class=\"progress\" style=\"margin-top: 2%; height: 32px;\"><div class=\"progress-bar progress-bar-success font-weight-bolder\" role=\"progressbar\" aria-valuenow=\""+progress+"\""+
                    "aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"+progress+"%; font-size: 16px\">Student's progress "+progress+"%</div></div>" +
                    "<div class=\"container\"><div class=\"row\"><div class=\"col-md-8\"><div class=\"container d-flex\" style=\"margin-left: 20%\">\n" +
                    "<div class=\"shadow p-3 mb-5 bg-white rounded\"><div class=\"card text-center \" style=\"width: 45rem;\"><div class=\"card-body\">\n" +
                    "<h5 class=\"card-title\">Theory</h5><h6 class=\"card-subtitle mb-2 text-muted\">Sections</h6><div class = \"col\">\n" +
                    "<form method=\"post\" action=\"/Student_CourseHomepage\"><ul class=\"list-group list-group-flush\"><div class=\"accordion\" id=\"accordionExample\">");

            TheoryMapper t = new TheoryMapper();
            ResultSet theory = t.get_theory_(theory_id);
            while (theory.next()){
                PrintSections(theory.getString("theory_id"), theory_id, theory.getString("section_data"), out);
            }

            out.println("</ul></form><br><form method=\"post\" action=\"/StudentHomepage\"><input type=\"submit\" id=\"log\" value=\"Star the test\" name=\"logout\">\n" +
                    "</form></div></div></div></div></div></div><div class=\"col-md-4\"><img src=\"./img/bugs_bunny.png\" alt=\"Instructions\"  width=\"400\" height=\"600\">\n" +
                    "</div></div></div><script src=\"./bootstrap/js/bootstrap.bundle.js\"></script>\n" +
                    "<script src=\"./bootstrap/js/bootstrap.js\"></script>/body></html>");
            }
        catch(Exception e){ System.out.println(e);}
    }
    protected void PrintSections(String theory_id, String s_theory_id, String section_data, PrintWriter out) {
        if(theory_id.equals(s_theory_id)){
            out.println(" <div id=\"heading"+s_theory_id+"\">\n" +
                    "<button style=\"border-bottom: double;\" class=\"list-group-item list-group-item-action\" type=\"button\" data-toggle=\"collapse\" data-target=\"#collapse"+s_theory_id+"\" aria-expanded=\"false\" aria-controls=\"collapse"+s_theory_id+"\">\n" +
                    "   Multiplication of "+s_theory_id+"</button></div><div id=\"collapse"+s_theory_id+"\" class=\"collapse show\" aria-labelledby=\"heading"+s_theory_id+"\" data-parent=\"#accordionExample\">\n" +
                    "<br>"+section_data+"</div>");
        }else{
            out.println("<div id=\"heading"+theory_id+"\"><button style=\"border-bottom: double;\" class=\"list-group-item list-group-item-action\"  type=\"button\" data-toggle=\"collapse\" data-target=\"#collapse"+theory_id+"\" aria-expanded=\"false\" aria-controls=\"collapse"+theory_id+"\">\n" +
                    "   Multiplication of "+theory_id+"</button></div><div id=\"collapse"+theory_id+"\" class=\"collapse\" aria-labelledby=\"heading"+theory_id+"\" data-parent=\"#accordionExample\">\n" +
                    "<br>"+section_data+"</div></div>");
        }
    }
}
