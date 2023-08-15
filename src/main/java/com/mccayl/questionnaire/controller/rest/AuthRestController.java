package com.mccayl.questionnaire.controller.rest;

import com.mccayl.questionnaire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    //private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

//    @PostMapping("signup")
//    public ResponseEntity<?> signup(@Valid @RequestBody SignupDTO dto) {
//        User user = new User();
//        user.setEmail(dto.getEmail());
//        user.setPassword(passwordEncoder.encode(dto.getPassword()));
//        userService.save(user);
//
//        String accessToken = jwtUtil.generateAccessToken(user);
//        String refreshToken = jwtUtil.generateRefreshToken(user);
//
//        return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken));
//    }
//
//    @PostMapping("login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
//        Authentication authentication =
//                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User user = (User) authentication.getPrincipal();
//
//        String accessToken = jwtUtil.generateAccessToken(user);
//        String refreshToken = jwtUtil.generateRefreshToken(user);
//
//        return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken));
//    }
//
//    @PostMapping("access-token")
//    public ResponseEntity<?> accessToken(@RequestBody TokenDTO dto) {
//        String refreshToken = dto.getRefreshToken();
//        if (jwtUtil.validateRefreshToken(refreshToken)) {
//            User user = (User) ((UserServiceImpl) userService).loadUserByUsername(jwtUtil.getUsernameFromRefreshToken(refreshToken));
//            String accessToken = jwtUtil.generateAccessToken(user);
//            return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken));
//        }
//
//        throw new BadCredentialsException("invalid token");
//    }
//
//    @PostMapping("refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestBody TokenDTO dto) {
//        String refreshToken = dto.getRefreshToken();
//        if (jwtUtil.validateRefreshToken(refreshToken)) {
//            User user = (User) ((UserServiceImpl) userService).loadUserByUsername(jwtUtil.getUsernameFromRefreshToken(refreshToken));
//            String accessToken = jwtUtil.generateAccessToken(user);
//            String newRefreshToken = jwtUtil.generateRefreshToken(user);
//            return ResponseEntity.ok(new TokenDTO(accessToken, newRefreshToken));
//        }
//
//        throw new BadCredentialsException("invalid token");
//    }
}
