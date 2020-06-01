package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    PageGenerator pageGenerator = PageGenerator.getPageGenerator();
    UserService userService = UserService.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(pageGenerator.getPage("registerPage.html", Collections.emptyMap()));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String emailInput = req.getParameter("email");
        String passwordInput = req.getParameter("password");

        if (emailInput.length() > 0 & passwordInput.length() > 0) {
            userService.addUser(new User(emailInput, passwordInput));
            resp.getWriter().println("registrated: " + userService.getAllUsers());
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(pageGenerator.getPage("registerPage.html", Collections.emptyMap()));
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
