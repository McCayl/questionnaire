package com.mccayl.questionnaire.security;

import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SetPasswordAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String setPasswordPath = path.substring(0, path.lastIndexOf('/') + 1);

        if ("/auth/setpassword/".equals(setPasswordPath)) {
            String token = path.substring(path.lastIndexOf('/') + 1);
            if (!jwtService.isTokenValid(token))
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        filterChain.doFilter(request, response);
    }
}
