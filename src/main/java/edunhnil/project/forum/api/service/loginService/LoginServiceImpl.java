package edunhnil.project.forum.api.service.loginService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edunhnil.project.forum.api.dao.informationRepository.Information;
import edunhnil.project.forum.api.dao.informationRepository.InformationRepository;
import edunhnil.project.forum.api.dao.userRepository.CustomUserDetail;
import edunhnil.project.forum.api.dao.userRepository.User;
import edunhnil.project.forum.api.dao.userRepository.UserRepository;
import edunhnil.project.forum.api.dto.informationDTO.InformationRequest;
import edunhnil.project.forum.api.dto.loginDTO.LoginRequest;
import edunhnil.project.forum.api.dto.loginDTO.LoginResponse;
import edunhnil.project.forum.api.dto.loginDTO.RegisterRequest;
import edunhnil.project.forum.api.dto.userDTO.UserRequest;
import edunhnil.project.forum.api.exception.InvalidRequestException;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.jwt.JwtTokenProvider;
import edunhnil.project.forum.api.service.AbstractService;
import edunhnil.project.forum.api.ultilities.InputValidation;

@Service
public class LoginServiceImpl extends AbstractService<UserRepository> implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private InformationRepository informationRepository;

    public void validateRequestParam(InformationRequest informationRequest) {
        InputValidation.validDateFormat(informationRequest.getDob());
        InputValidation.validEmail(informationRequest.getEmail());
        InputValidation.validMobile(informationRequest.getPhone());
        InputValidation.validGender(informationRequest.getGender());
    }

    @Override
    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        validate(loginRequest);
        User user = repository.getUserByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Not found user with username: " + loginRequest.getUsername()));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidRequestException("password does not match");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
        repository.changeEnabled(1, user.getId());
        return Optional.of(new LoginResponse(jwt));
    }

    @Override
    public void logout(String username) {
        User user = repository.getUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Not found user with username: " + username));
        repository.changeEnabled(0, user.getId());
    }

    @Override
    public void register(RegisterRequest RegisterRequest) {
        if (repository.checkExistedUsername(RegisterRequest.getUsername())) {
            throw new InvalidRequestException("username existed");
        }
        UserRequest userRequest = new UserRequest(RegisterRequest.getUsername(), RegisterRequest.getPassword(),
                "ROLE_USER", 0);
        validate(userRequest);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = bCryptPasswordEncoder.encode(RegisterRequest.getPassword());
        userRequest.setPassword(passwordEncode);
        repository.resetId();
        repository.addNewUser(objectMapper.convertValue(userRequest, User.class));
        User user = repository.getUserByUsername(userRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("not found username"));
        InformationRequest informationRequest = new InformationRequest(RegisterRequest.getFirstName(),
                RegisterRequest.getLastName(), user.getId(), RegisterRequest.getGender(), RegisterRequest.getDob(),
                RegisterRequest.getAddress(), RegisterRequest.getEmail(), RegisterRequest.getPhone());
        validate(informationRequest);
        validateRequestParam(informationRequest);
        informationRepository.resetId();
        informationRepository.addNewInformation(objectMapper.convertValue(informationRequest, Information.class));
    }

}
