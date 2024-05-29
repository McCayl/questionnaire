package com.mccayl.questionnaire.security;

import com.mccayl.questionnaire.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidRequestTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> requestToken = Optional.ofNullable(request.getParameter("token"));

        if (requestToken.isPresent() && !jwtService.isTokenValid(requestToken.get()))
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        filterChain.doFilter(request, response);
    }
}
