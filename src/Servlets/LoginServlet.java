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

        String username = request.getParameter("userid").toLowerCase();
        String pass = request.getParameter("pass").toLowerCase();
        HttpSession session=request.getSession();                                   //creates a session for the user

        try{
            //check for student login
            StudentMapper sm = new StudentMapper();
            if(!(sm.login(username,pass).isBlank())) {                               //if student's credentials are correct
                session.setAttribute("userid", sm.login(username,pass));
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/S_Welcome");
                dispatcher.forward(request, response);
            }else {                                                                //if student doesn't exist check for teacher
                TeacherMapper tm = new TeacherMapper();
                if(!tm.login(username,pass).isBlank()) {                            //if teacher's credentials are correct
                    session.setAttribute("userid", tm.login(username,pass));
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/T_Welcome");
                    dispatcher.forward(request, response);
                }else{
                    response.sendRedirect("login.html");
                    return;
                }
            }
        }catch (Exception e){
            System.out.println(e);
            response.sendRedirect("login.html");
            return;
        }
    }
}
