package edunhnil.project.forum.api.service.loginService;

import java.util.Optional;

import edunhnil.project.forum.api.dto.loginDTO.LoginRequest;
import edunhnil.project.forum.api.dto.loginDTO.LoginResponse;
import edunhnil.project.forum.api.dto.loginDTO.RegisterRequest;

public interface LoginService {
    Optional<LoginResponse> login(LoginRequest loginRequest);

    void logout(String username);

    void register(RegisterRequest RegisterRequest);
}
