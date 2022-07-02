package edunhnil.project.forum.api.service.userService;

import java.util.Map;
import java.util.Optional;

import edunhnil.project.forum.api.dto.userDTO.ChangePasswordReq;
import edunhnil.project.forum.api.dto.userDTO.ChangeUsernameReq;
import edunhnil.project.forum.api.dto.userDTO.ListUserResponse;
import edunhnil.project.forum.api.dto.userDTO.UserRequest;
import edunhnil.project.forum.api.dto.userDTO.UserResponse;

public interface UserService {
    Optional<ListUserResponse> getUsers(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField);

    Optional<UserResponse> getUserById(int id);

    void addNewUser(UserRequest userRequest);

    void updateUserById(UserRequest userRequest, int id);

    void deleteUserById(int id);

    void changePasswordById(ChangePasswordReq changePassword, int id);

    void changeUsernameById(ChangeUsernameReq changeUsername, int id);

}
