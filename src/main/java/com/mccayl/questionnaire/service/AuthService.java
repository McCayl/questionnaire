package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.dto.LoginDTO;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    void signup(String email);
    void signin(LoginDTO loginDTO);
    void logout(HttpServletRequest request);
    void setPassword(String token, String password);
}
