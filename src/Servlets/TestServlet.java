package Servlets;

import Classes.TestMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

@WebServlet(name = "TestServlet",value ="/Test")
public class TestServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userid = (String)request.getSession().getAttribute("userid");        //get user id from session
        String theory_id = (String)request.getSession().getAttribute("theory_id");
        PrintWriter out = response.getWriter();
        try{
            TestMapper test = new TestMapper();
            String href = test.get_test(theory_id);
            out.println("<!DOCTYPE html><html><head></head><body>\n" +
                    "<script>window.open(\""+href+"\",\"_self\");</script></body></html>");
        }catch (Exception e){System.out.println(e);}
    }
}
