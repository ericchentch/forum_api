package edunhnil.project.forum.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edunhnil.project.forum.api.dto.userDTO.ChangePasswordReq;
import edunhnil.project.forum.api.dto.userDTO.ChangeUsernameReq;
import edunhnil.project.forum.api.dto.userDTO.ListUserResponse;
import edunhnil.project.forum.api.dto.userDTO.UserRequest;
import edunhnil.project.forum.api.dto.userDTO.UserResponse;
import edunhnil.project.forum.api.service.userService.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "account")
@CrossOrigin(origins = "http://localhost:3000/", methods = { RequestMethod.OPTIONS, RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }, allowedHeaders = "*", allowCredentials = "true")
public class UserController extends AbstractController<UserService> {

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "admin/getUsers")
    public ResponseEntity<ListUserResponse> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam Map<String, String> allParams,
            @RequestParam(defaultValue = "asc") String keySort,
            @RequestParam(defaultValue = "modified") String sortField) {
        return response(service.getUsers(allParams, keySort, page, pageSize, sortField));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "admin/getUserDetail/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        return response(service.getUserById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "admin/addNewUser")
    public void addNewUser(@RequestBody UserRequest userRequest) {
        service.addNewUser(userRequest);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "admin/updateUser/{id}")
    public void updateUserAdmin(@PathVariable int id, @RequestBody UserRequest userRequest) {
        service.updateUserById(userRequest, id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "admin/deleteUser/{id}")
    public void deleteUserUser(@PathVariable int id) {
        service.deleteUserById(id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "user/updateUser/{id}")
    public void updateUserUser(@PathVariable int id, @RequestBody UserRequest userRequest) {
        service.updateUserById(userRequest, id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "user/deleteUser/{id}")
    public void deleteUserAdmin(@PathVariable int id) {
        service.deleteUserById(id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "user/{id}/changePassword")
    public void changePassword(@RequestBody ChangePasswordReq changePassword, @PathVariable int id) {
        service.changePasswordById(changePassword, id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "user/{id}/changeUsername")
    public void changeUsername(@RequestBody ChangeUsernameReq changeUsername, @PathVariable int id) {
        service.changeUsernameById(changeUsername, id);
    }
}
