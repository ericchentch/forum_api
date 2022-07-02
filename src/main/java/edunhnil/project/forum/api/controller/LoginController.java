package edunhnil.project.forum.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edunhnil.project.forum.api.dao.userRepository.CustomUserDetail;
import edunhnil.project.forum.api.dto.loginDTO.LoginRequest;
import edunhnil.project.forum.api.dto.loginDTO.LoginResponse;
import edunhnil.project.forum.api.dto.loginDTO.RegisterRequest;
import edunhnil.project.forum.api.service.loginService.LoginService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
@CrossOrigin(origins = "http://localhost:3000/", methods = { RequestMethod.OPTIONS, RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE }, allowedHeaders = "*", allowCredentials = "true")
public class LoginController extends AbstractController<LoginService> {

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return response(service.login(loginRequest));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
        String username = userDetail.getUsername();
        service.logout(username);
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>(username + " logout!", null, HttpStatus.OK.value());
    }

    @PostMapping(value = "register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        service.register(registerRequest);
    }
}
