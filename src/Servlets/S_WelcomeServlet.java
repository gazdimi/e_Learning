package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "S_WelcomeServlet",value ="/S_Welcome")
public class S_WelcomeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userid = (String)request.getSession().getAttribute("userid");        //get user id from session
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>Student Homepage</title>\n" +
                "        <link href=\"./bootstrap/css/bootstrap.css\" rel=\"stylesheet\">\n" +
                "        <link href=\"./bootstrap/css/bootstrap-grid.css\" rel=\"stylesheet\">\n" +
                "        <link href=\"./bootstrap/css/bootstrap-reboot.css\" rel=\"stylesheet\">\n" +
                "        <script src=\"//code.jquery.com/jquery-1.11.0.min.js\"></script>\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
                "        <style>\n" +
                "            .sidenav {\n" +
                "                height: 100%;\n" +
                "                width: auto;\n" +
                "                position: fixed;\n" +
                "                z-index: 1;\n" +
                "                top: 0;\n" +
                "                left: 0;\n" +
                "                background-color: #042611;\n" +
                "                overflow-x: hidden;\n" +
                "                padding-top: 20px;\n" +
                "            }\n" +
                "            .sidenav a {\n" +
                "                padding: 6px 20px 6px 20px;\n" +
                "                text-decoration: none;\n" +
                "                font-size: 21px;\n" +
                "                color: #5f6e62;\n" +
                "                display: block;\n" +
                "            }\n" +
                "            .sidenav a:hover {\n" +
                "                color: #edede4;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body style=\"background: #FFEEEE;  /*for old browsers */\n" +
                "    background: -webkit-linear-gradient(to right, #DDEFBB, #FFEEEE);  /* Chrome 10-25, Safari 5.1-6 */\n" +
                "    background: linear-gradient(to right, #DDEFBB, #FFEEEE); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */\">\n" +
                "        <div class=\"sidenav\">\n" +
                "            <div class=\"accordion\" id=\"accordion1\">\n" +
                "                <div id=\"heading1\">\n" +
                "                    <a class=\"card-title\" href=\"#about\" data-toggle=\"collapse\" data-target=\"#collapse1\" aria-expanded=\"false\" aria-controls=\"collapse1\">Profile Info</a>\n" +
                "                </div>\n" +
                "                <div id=\"collapse1\" class=\"collapse container text-white\" style=\"width: fit-content; background-color: #095426\" aria-labelledby=\"headingOne\" data-parent=\"#accordion1\">\n" +
                "                    <p class=\"card-text\"><span class=\"glyphicon glyphicon-user\"></span> Kostas Dimitrakopoulos</p>\n" +
                "                    <p class=\"card-text\">Current Section: <p>  Multiplication of 3</p></p>\n" +
                "                    <p class=\"card-text\">Progress: 40%</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <a href=\"/S_Welcome\">Home</a>\n" +
                "            <a href=\"#contact\">Contact</a>\n" +
                "            <a href=\"#logout\">Logout</a>\n" +
                "        </div>\n" +
                "        <div class=\"progress\" style=\"margin-top: 2%; height: 32px;\">\n" +
                "            <div class=\"progress-bar progress-bar-success font-weight-bolder\" role=\"progressbar\" aria-valuenow=\"40\"\n" +
                "                 aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:40%; font-size: 16px\">\n" +
                "                Student's progress 40%\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-md-8\">\n" +
                "                    <div class=\"container d-flex\" style=\"margin-left: 20%\">\n" +
                "                        <div class=\"shadow p-3 mb-5 bg-white rounded\">\n" +
                "                            <div class=\"card text-center \" style=\"width: 45rem;\">\n" +
                "                                <div class=\"card-body\">\n" +
                "                                    <h5 class=\"card-title\">Theory</h5>\n" +
                "                                    <h6 class=\"card-subtitle mb-2 text-muted\">Sections</h6>\n" +
                "                                    <div class = \"col\">\n" +
                "                                        <form method=\"post\" action=\"/Student_CourseHomepage\">\n" +
                "                                            <ul class=\"list-group list-group-flush\">\n" +
                "                                                <div class=\"accordion\" id=\"accordionExample\">\n" +
                "                                                    <div id=\"headingOne\">\n" +
                "                                                        <button style=\"border-bottom: double;\" class=\"list-group-item list-group-item-action\" type=\"button\" data-toggle=\"collapse\" data-target=\"#collapseOne\" aria-expanded=\"false\" aria-controls=\"collapseOne\">\n" +
                "                                                            Multiplication of 1\n" +
                "                                                        </button>\n" +
                "                                                    </div>\n" +
                "                                                    <div id=\"collapseOne\" class=\"collapse\" aria-labelledby=\"headingOne\" data-parent=\"#accordionExample\">\n" +
                "                                                        <br>1 x 1 = 1<br>1 x 2 = 2<br>1 x 3 = 3<br>1 x 4 = 4<br>1 x 5 = 5<br>1 x 6 = 6<br>1 x 7 = 7<br>1 x 8 = 8<br>1 x 9 = 9<br>1 x 10 = 10\n" +
                "                                                    </div>\n" +
                "                                        <!--<input type=\"submit\"  name=\"coursename\" value=\"ΑΛΓΟΡΙΘΜΟΙ\" class=\"list-group-item list-group-item-action\">-->\n" +
                "                                                    <div id=\"headingTwo\">\n" +
                "                                                        <button style=\"border-bottom: double;\" class=\"list-group-item list-group-item-action\"  type=\"button\" data-toggle=\"collapse\" data-target=\"#collapseTwo\" aria-expanded=\"false\" aria-controls=\"collapseTwo\">\n" +
                "                                                            Multiplication of 2\n" +
                "                                                        </button>\n" +
                "                                                    </div>\n" +
                "                                                    <div id=\"collapseTwo\" class=\"collapse\" aria-labelledby=\"headingTwo\" data-parent=\"#accordionExample\">\n" +
                "                                                        <br>1 x 2 = 2<br>2 x 2 = 4<br>3 x 2 = 6<br>4 x 2 = 8<br>5 x 2 = 10<br>6 x 2 = 12<br>7 x 2 = 14<br>8 x 2 = 16<br>9 x 2 = 18<br>10 x 2 = 20\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </ul>\n" +
                "                                        </form>\n" +
                "                                        <br>\n" +
                "                                        <form method=\"post\" action=\"/StudentHomepage\">\n" +
                "                                            <input type=\"submit\" id=\"log\" value=\"Star the test\" name=\"logout\">\n" +
                "                                        </form>\n" +
                "                                        <!--<br>\n" +
                "                                        <form method=\"post\" action=\"/StudentHomepage\">\n" +
                "                                            <input type=\"submit\" id=\"log\" value=\"Logout\" name=\"logout\">\n" +
                "                                        </form>-->\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"col-md-4\">\n" +
                "                    <img src=\"./img/bugs.png\" alt=\"Instructions\"  width=\"400\" height=\"600\">\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <script src=\"./bootstrap/js/bootstrap.bundle.js\"></script>\n" +
                "        <script src=\"./bootstrap/js/bootstrap.js\"></script>\n" +
                "    </body>\n" +
                "</html>\n");
    }
}
