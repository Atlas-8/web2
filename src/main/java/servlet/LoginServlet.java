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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    PageGenerator pageGenerator = PageGenerator.getPageGenerator();
    UserService userService = UserService.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String emailInput = req.getParameter("email");
        String passwordInput = req.getParameter("password");

        User user = new User(emailInput, passwordInput);

        if (userService.getAllUsers().contains(user)) {
            int userIndex = userService.getAllUsers().indexOf(user);

            userService.authUser(userService.getAllUsers().get(userIndex));

            resp.getWriter().println("authorised: " + userService.getAllAuth());

        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(pageGenerator.getPage("authPage.html", Collections.emptyMap()));
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(pageGenerator.getPage("authPage.html", Collections.emptyMap()));
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}
