package com.github.Darncol.servlets;

import com.github.Darncol.managers.MatchManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var manager = new MatchManager();

        String player1 = req.getParameter("playerOne");
        String player2 = req.getParameter("playerTwo");

        try {
            UUID uuid = manager.startNewMatch(player1, player2);

            HttpSession session = req.getSession();
            session.setAttribute("uuid", uuid);

            resp.sendRedirect("match-score?uuid=" + uuid);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?message=" + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/index.jsp?message=Something went wrong with bad words validation :(");
        }
    }
}
