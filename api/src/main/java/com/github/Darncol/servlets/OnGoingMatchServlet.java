package com.github.Darncol.servlets;

import com.github.Darncol.Match;
import com.github.Darncol.managers.MatchManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet("/match-score")
public class OnGoingMatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var manager = new MatchManager();

        String parameter = req.getParameter("uuid");
        UUID uuid = UUID.fromString(parameter);

        Match match = manager.getMatch(uuid);

        req.setAttribute("player1", match.getPlayer1DTO());
        req.setAttribute("player2", match.getPlayer2DTO());
        req.getRequestDispatcher("match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var manager = new MatchManager();

        HttpSession session = req.getSession();
        UUID uuid = (UUID) session.getAttribute("uuid");
        String roundWinner = req.getParameter("roundWinner");

        if (roundWinner == null || roundWinner.isEmpty()) {
            String message = "invalid round winner";
            resp.sendRedirect(req.getContextPath() + "/index.jsp?message=" + message);
            return;
        }

        if (uuid == null) {
            String message = "invalid uuid";
            resp.sendRedirect(req.getContextPath() + "/index.jsp?message=" + message);
            return;
        }

        try {
            manager.nextRound(roundWinner, uuid);
        } catch (IllegalStateException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?message=" + e.getMessage());
        }

        try {
            if (manager.validateWinCondition(uuid)) {
                session.removeAttribute("uuid");
                String message = manager.getWinnerName(uuid) + " Win the game!";
                manager.finishMatch(uuid);
                resp.sendRedirect(req.getContextPath() + "/index.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8));
                return;
            }
        } catch (IllegalStateException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?message=" + e.getMessage());
        }

        req.setAttribute("player1", manager.getPlayer1DTO(uuid));
        req.setAttribute("player2", manager.getPlayer2DTO(uuid));
        req.getRequestDispatcher("match-score.jsp").forward(req, resp);
    }
}
