package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.dto.EmailDTO;
import com.mccayl.questionnaire.dto.LoginDTO;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.repo.RoleRepository;
import com.mccayl.questionnaire.service.AuthService;
import com.mccayl.questionnaire.service.EmailService;
import com.mccayl.questionnaire.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public void signup(String email) throws BadCredentialsException {
        try {
            userDetailsService.loadUserByUsername(email);
            throw new BadCredentialsException("Такой пользователь уже зарегестрирован");
        } catch (UsernameNotFoundException e) {
            User preAuthUser = new User();
            preAuthUser.setEmail(email);
            String setPasswordToken = jwtService.generateToken(preAuthUser);
            String setPasswordUrl = "http://localhost:8080/auth/setpassword/?token=" + setPasswordToken;

            emailService.sendSimpleMail(new EmailDTO(
                    email,
                    "Смена пароля",
                    setPasswordUrl));
        }
    }

    @Override
    public void signin(LoginDTO loginDTO) {
        authenticate(loginDTO.getEmail(), loginDTO.getPassword(), null);
    }

    @Override
    public void logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
    }

    private void authenticate(String email,
                              String password,
                              Collection<? extends GrantedAuthority> authorities) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password, authorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    public void setPassword(String token, String password) {
        User user = new User();
        user.setEmail(jwtService.extractUserName(token));
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(roleRepository.findByName("ROLE_USER"));
        UserServiceImpl userService = (UserServiceImpl) userDetailsService;
        userService.save(user);
    }
}
