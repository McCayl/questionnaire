package com.mccayl.questionnaire.controller;

import com.mccayl.questionnaire.dto.LoginDTO;
import com.mccayl.questionnaire.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/signin")
    public String signinPage(Model model) {
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute("login", loginDTO);
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute("login") LoginDTO loginDTO) {
        authService.signin(loginDTO);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("email", null);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("email") String email) {
        authService.signup(email);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        authService.logout(request);
        return "redirect:/";
    }

    @GetMapping("/setpassword/{token}")
    public String setPasswordPage(@PathVariable String token, Model model) {
        model.addAttribute("password", null);
        model.addAttribute("token", token);
        return "setPassword";
    }

    @PostMapping("/setpassword/{token}")
    public String setPassword(@PathVariable String token, @ModelAttribute("password") String password) {
        authService.setPassword(token, password);
        return "redirect:/auth/signin";
    }
}
