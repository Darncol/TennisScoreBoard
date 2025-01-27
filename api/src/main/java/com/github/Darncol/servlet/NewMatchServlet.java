package com.github.Darncol.servlet;

import com.github.Darncol.ChatGPTException;
import com.github.Darncol.InvalidDataException;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InvalidDataException, ChatGPTException {
        var manager = new MatchManager();

        String player1 = req.getParameter("playerOne");
        String player2 = req.getParameter("playerTwo");

        try {
            UUID uuid = manager.startNewMatch(player1, player2);

            HttpSession session = req.getSession();
            session.setAttribute("uuid", uuid);

            resp.sendRedirect("match-score?uuid=" + uuid);
        } catch (InterruptedException | ChatGPTException e) {
            throw new ChatGPTException(e.getMessage());
        } catch (InvalidDataException e) {
            throw new InvalidDataException(e.getMessage());
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
