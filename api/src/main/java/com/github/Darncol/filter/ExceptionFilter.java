package com.github.Darncol.filter;

import com.github.Darncol.ChatGPTException;
import com.github.Darncol.DataBaseException;
import com.github.Darncol.DataNotFoundException;
import com.github.Darncol.InvalidDataException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InterruptedIOException;

@WebFilter("/*")
public class ExceptionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(req, res, chain);
        } catch (DataBaseException | ChatGPTException | DataNotFoundException | IOException | ServletException e) {
            String message = e.getMessage();
            res.sendRedirect(req.getContextPath() + "/index.jsp?message=" + message);
        } catch (InvalidDataException e) {
            String message = e.getMessage();
            res.sendRedirect(req.getContextPath() + "/new-match.jsp?message=" + message);
        }
    }
}
