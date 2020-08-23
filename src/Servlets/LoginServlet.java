package Servlets;

import Classes.StudentMapper;
import Classes.TeacherMapper;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet",value ="/Login")
public class LoginServlet extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("userid");
        String pass = request.getParameter("pass");
        HttpSession session=request.getSession();                                   //creates a session for the user

        try{
            //check for student login
            StudentMapper sm = new StudentMapper();
            if(sm.login(username,pass)) {                                          //if student's credentials are correct
                session.setAttribute("username", username);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/StudentHomepage");
                dispatcher.forward(request, response);
            }else {                                                                //if student doesn't exist check for teacher
                TeacherMapper tm = new TeacherMapper();
                if(tm.login(username,pass)) {                                      //if teacher's credentials are correct
                    session.setAttribute("username", username);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/TeacherHomepage");
                    dispatcher.forward(request, response);
                }else{
                    response.sendRedirect("index.html");
                    return;
                }
            }
        }catch (Exception e){
            response.sendRedirect("index.html");
            return;
        }
    }
}
