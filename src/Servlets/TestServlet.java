package Servlets;

import Classes.GoogleFormsMapper;
import Classes.StudentMapper;
import Classes.Students_TestsMapper;
import Classes.TestMapper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.ResultSet;

@WebServlet(name = "TestServlet",value ="/Test")
public class TestServlet extends HttpServlet {

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
            String score = "";
            if(request.getParameter("back_to_homepage")!=null){
                score = request.getParameter("get_score");
                Students_TestsMapper stu_test = new Students_TestsMapper();
                StudentMapper stum = new StudentMapper();
                boolean flag = stu_test.check_for_student_test(userid,S_WelcomeServlet.theory_id);

                if(Integer.parseInt(score)>=3){                                                         //success
                    int temp = Integer.parseInt(S_WelcomeServlet.theory_id) + 1;
                    stum.update_info(userid, String.valueOf(temp));
                    if(flag){ //student-test association exists
                        stu_test.update_student_test(userid, S_WelcomeServlet.theory_id,score, false);
                    }else{
                        stu_test.add_student_test(userid, S_WelcomeServlet.theory_id, score, false);
                    }
                }else{                                                                              //fail, add weakness
                    if (flag){ //student-test association exists
                        stu_test.update_student_test(userid, S_WelcomeServlet.theory_id,score, true);
                    }else{
                        stu_test.add_student_test(userid, S_WelcomeServlet.theory_id, score, true);
                    }
                }
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/S_Welcome");
                dispatcher.forward(request, response);
            }
            TestMapper test = new TestMapper();
            ResultSet res = test.get_test(S_WelcomeServlet.theory_id);
            out.println( S_WelcomeServlet.first_name + " " + S_WelcomeServlet.last_name + "</p><p class=\"card-text\">Current Section: <p>  Multiplication of " + S_WelcomeServlet.theory_id +
                    "</p></p><p class=\"card-text\">Progress: " + S_WelcomeServlet.progress + "%</p></div></div><form method=\"post\" action=\"/S_Welcome\"><a href=\"contact.html\">Contact</a>" +
                    "<a href=\"about.html\">About</a><a><input type=\"submit\" value=\"Logout\" name=\"logout\" id=\"logout_form\" style=\"background:none; border-width:0px; padding-left: 0px;\"></a></form></div>" +
                    "<div class=\"progress\" style=\"margin-top: 2%; height: 32px;\"><div class=\"progress-bar progress-bar-success font-weight-bolder\" role=\"progressbar\" aria-valuenow=\""+S_WelcomeServlet.progress+"\""+
                    "aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"+S_WelcomeServlet.progress+"%; font-size: 16px\">Student's progress "+S_WelcomeServlet.progress+"%</div></div>" +
                    "<div class=\"container\"><div class=\"row\"><div class=\"col-md-8\"><div class=\"container d-flex\" style=\"margin-left: 20%\">\n" +
                    "<div class=\"shadow p-3 mb-5 bg-white rounded\"><div class=\"card text-center \" style=\"width: 45rem;\"><div class=\"card-body\">" +
                    "<h4 class=\"card-title\">It's Testing Time</h4><h6 class=\"card-subtitle mb-2 text-muted\">Choose one answer at a time</h6><div class = \"col\">\n" +
                    "<form><hr><ul class=\"list-group list-group-flush\"><div>");
            String a1 = ""; String a2 = ""; String a3 = ""; String a4 = "";
            while(res.next()){
                PrintOption(res.getString("q1"), "1", "2", "3",res.getString("o1"),res.getString("o2"),res.getString("o3"), out);
                a1 = res.getString("a1");
                PrintOption(res.getString("q2"), "4", "5", "6",res.getString("o4"),res.getString("o5"),res.getString("o6"), out);
                a2 = res.getString("a2");
                PrintOption(res.getString("q3"), "7", "8", "9",res.getString("o7"),res.getString("o8"),res.getString("o9"), out);
                a3 = res.getString("a3");
                PrintOption(res.getString("q4"), "10", "11", "12",res.getString("o10"),res.getString("o11"),res.getString("o12"), out);
                a4 = res.getString("a4");
            }
            GoogleFormsMapper gf = new GoogleFormsMapper();
            String google_form = gf.get_google_form_test(S_WelcomeServlet.theory_id);
            out.println("</div></ul><div class=\"form-inline\"><button type='button' style=\"margin-left: 150px\"  class=\"btn btn-success\" onclick='displayAnswer()'>Submit</button>\n" +
                    "<label  style=\"margin-left: 50px\" id=\"total\" name=\"points\"></label></div></form><div id=\"success\" style=\"display: none;\">\n" +
                    "<hr><label>Succeed</label><p>Well done buddy, you're doing great.</p><p><a href=\"#\" onclick=\"window.open('"+google_form+"');\">Want more tests? Let's give it a try!!!</a></p>\n" +
                    "<form method=\"post\" action=\"/Test\"><input type=\"submit\" class=\"btn btn-success\" name=\"back_to_homepage\" value=\"Back to homepage\">\n" +
                    "<input type=\"hidden\" id=\"s_score\" name=\"get_score\" value=\"score\"></form><br></div><div id=\"fail\" style=\"display: none;\"><hr><label>Failed</label><p>Sorry buddy, read the theory and try again later.</p>\n" +
                    "<form method=\"post\" action=\"/Test\"><input type=\"submit\" class=\"btn btn-success\" name=\"back_to_homepage\" value=\"Back to homepage\">\n" +
                    "<input type=\"hidden\" id=\"f_score\" name=\"get_score\" value=\"score\"></form><br></div></div></div></div></div></div></div><div class=\"col-md-4\"><img src=\"img/test.png\" alt=\"Instructions\" width=\"400\" height=\"600\">\n" +
                    "</div></div></div><script>function displayAnswer() {var i; var score = 0; for (i = 1; i <= 12; i++) { if(i== "+a1+" || i== "+a2+" || i== "+a3+" || i== "+a4+"){\n" +
                    "if (document.getElementById('o'+i).checked) { document.getElementById('b'+i).style.border = '3px solid limegreen';\n" +
                    "document.getElementById('r'+i).style.color = 'limegreen'; document.getElementById('r'+i).innerHTML = ' Correct!'; score++;}\n" +
                    "}else{ if (document.getElementById('o'+i).checked) { document.getElementById('b'+i).style.border = '3px solid red'\n" +
                    "document.getElementById('r'+i).style.color = 'red'; document.getElementById('r'+i).innerHTML = ' Incorrect!';}}}\n" +
                    "$(':radio:not(:checked)').attr('disabled', true); document.getElementById(\"total\").innerHTML = \"Total points \" + score +\"/4\";\n" +
                    "if(score>=3){ document.getElementById('success').style.display = \"initial\"; document.getElementById('fail').style.display = \"none\"; document.getElementById(\"s_score\").value = \"\"+score+\"\";\n" +
                    "}else{ document.getElementById('success').style.display = \"none\"; document.getElementById('fail').style.display = \"initial\"; document.getElementById(\"f_score\").value = \"\"+score+\"\";}\n" +
                    "}</script><script src=\"./bootstrap/js/bootstrap.bundle.js\"></script><script src=\"./bootstrap/js/bootstrap.js\"></script></body></html>");

        }catch (Exception e){System.out.println(e);}
    }
    protected void PrintOption(String Q, String i1, String i2, String i3,String o1,String o2,String o3, PrintWriter out){
        out.println("<h4>"+Q+"</h4><div id='b"+i1+"'><label for='o"+i1+"' class=\"h4\">\n" +
                "<input type='radio' name='option"+i1+"' id='o"+i1+"' style='transform: scale(1.3); margin-right: 10px; vertical-align: middle; margin-top: -2px;' required/>\n" +
                ""+o1+"</label><span id='r"+i1+"'></span></div>\n" +
                "<div id='b"+i2+"'><label for='o"+i2+"' class=\"h4\">\n" +
                "<input type='radio' name='option"+i1+"' id='o"+i2+"' style='transform: scale(1.3); margin-right: 10px; vertical-align: middle; margin-top: -2px;' />\n" +
                ""+o2+"</label><span id='r"+i2+"'></span></div>\n" +
                "<div id='b"+i3+"'><label for='o"+i3+"' class=\"h4\">\n" +
                "<input type='radio' name='option"+i1+"' id='o"+i3+"' style='transform: scale(1.3); margin-right: 10px; vertical-align: middle; margin-top: -2px;' />\n" +
                ""+o3+"</label><span id='r"+i3+"'></span></div>\n" +
                "<small class=\"text-muted\" style=\"margin-left: 70%\">1 point</small><hr />");

    }
}
