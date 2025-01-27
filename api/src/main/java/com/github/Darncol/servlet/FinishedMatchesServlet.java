package com.github.Darncol.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Darncol.Match;
import com.github.Darncol.services.FinishedMatchesPersistenceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        String name = req.getParameter("filter_by_player_name");

        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET");

        List<Match> matches = name == null || name.isEmpty() ?
                FinishedMatchesPersistenceService.getAllMatchesInPage(page) :
                FinishedMatchesPersistenceService.getMatchesWithPlayer(name, page);

        String json = mapper.writeValueAsString(matches);
        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
