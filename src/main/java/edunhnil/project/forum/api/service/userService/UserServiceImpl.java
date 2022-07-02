package edunhnil.project.forum.api.service.userService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edunhnil.project.forum.api.exception.InvalidRequestException;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.constant.DateTime;
import edunhnil.project.forum.api.dao.userRepository.User;
import edunhnil.project.forum.api.dao.userRepository.UserRepository;
import edunhnil.project.forum.api.dto.userDTO.ChangePasswordReq;
import edunhnil.project.forum.api.dto.userDTO.ChangeUsernameReq;
import edunhnil.project.forum.api.dto.userDTO.ListUserResponse;
import edunhnil.project.forum.api.dto.userDTO.UserRequest;
import edunhnil.project.forum.api.dto.userDTO.UserResponse;
import edunhnil.project.forum.api.service.AbstractService;
import edunhnil.project.forum.api.ultilities.DateFormat;

@Service
public class UserServiceImpl extends AbstractService<UserRepository> implements UserService {

        private String passwordEncoder(String password) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                return bCryptPasswordEncoder.encode(password);
        }

        @Override
        public Optional<ListUserResponse> getUsers(Map<String, String> allParams, String keySort, int page,
                        int pageSize,
                        String sortField) {
                List<User> users = repository
                                .getUsers(allParams, keySort, page, pageSize, sortField)
                                .get();
                return Optional.of(new ListUserResponse(
                                users.stream()
                                                .map(user -> new UserResponse(user.getId(),
                                                                user.getUsername(),
                                                                user.getRole(),
                                                                DateFormat.toDateString(user.getCreated(),
                                                                                DateTime.YYYY_MM_DD),
                                                                DateFormat.toDateString(user.getModified(),
                                                                                DateTime.YYYY_MM_DD),
                                                                user.getEnabled(),
                                                                user.getDeleted()))
                                                .collect(Collectors.toList()),
                                page, pageSize, repository.getTotalPage(allParams)));
        }

        @Override
        public Optional<UserResponse> getUserById(int id) {
                User user = repository.getUserById(id).orElseThrow(
                                () -> new ResourceNotFoundException("Not found user with id: " + id + "!"));
                return Optional.of(new UserResponse(user.getId(), user.getUsername(),
                                user.getRole(), DateFormat.toDateString(user.getCreated(), DateTime.YYYY_MM_DD),
                                DateFormat.toDateString(user.getModified(), DateTime.YYYY_MM_DD),
                                user.getEnabled(),
                                user.getDeleted()));
        }

        @Override
        public void addNewUser(UserRequest userRequest) {
                repository.resetId();
                validate(userRequest);
                userRequest.setPassword(passwordEncoder(userRequest.getPassword()));
                User user = objectMapper.convertValue(userRequest, User.class);
                repository.addNewUser(user);
        }

        @Override
        public void updateUserById(UserRequest userRequest, int id) {
                repository.getUserById(id).orElseThrow(
                                () -> new ResourceNotFoundException("Not found user with id: " + id + "!"));
                validate(userRequest);
                repository.updateUserById(id, objectMapper.convertValue(userRequest, User.class));
        }

        @Override
        public void deleteUserById(int id) {
                repository.getUserById(id).orElseThrow(
                                () -> new ResourceNotFoundException("Not found user with id: " + id + "!"));
                repository.deleteUserById(id);
        }

        @Override
        public void changePasswordById(ChangePasswordReq changePassword, int id) {
                validate(changePassword);
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                User user = repository.getUserById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found id: " + id));
                if (!bCryptPasswordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
                        throw new InvalidRequestException("Old password does not match!");
                }
                repository.changePasswordById(changePassword.getNewPassword(), id);
        }

        @Override
        public void changeUsernameById(ChangeUsernameReq changeUsername, int id) {
                validate(changeUsername);
                User user = repository.getUserById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found id: " + id));
                if (user.getUsername().compareTo(changeUsername.getOldUsername()) != 0) {
                        throw new InvalidRequestException("Old username is not match!");
                }
                repository.changeUserNameById(changeUsername.getNewUsername(), id);
        }

}
