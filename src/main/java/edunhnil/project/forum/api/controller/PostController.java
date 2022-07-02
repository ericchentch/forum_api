package edunhnil.project.forum.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edunhnil.project.forum.api.dto.postDTO.ListPostResponse;
import edunhnil.project.forum.api.dto.postDTO.PostRequest;
import edunhnil.project.forum.api.dto.postDTO.PostResponse;
import edunhnil.project.forum.api.dto.postDTO.PostUpdateReq;
import edunhnil.project.forum.api.service.postService.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "post")
@CrossOrigin(origins = "http://localhost:3000/", methods = { RequestMethod.OPTIONS, RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE }, allowedHeaders = "*", allowCredentials = "true")
public class PostController extends AbstractController<PostService> {

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "user/{id}/getListPost")
    public ResponseEntity<ListPostResponse> getUserPosts(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String keySort,
            @RequestParam(defaultValue = "modified") String sortField,
            @PathVariable(value = "id", required = true) int authorId,
            @RequestParam Map<String, String> allParams) {
        return response(service.getPostsByAuthorId(allParams, keySort, page, pageSize, sortField, authorId));
    }

    @GetMapping(value = "public/getListPost")
    public ResponseEntity<ListPostResponse> getPublicPosts(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String keySort,
            @RequestParam(defaultValue = "modified") String sortField,
            @RequestParam Map<String, String> allParams) {
        return response(service.getPublicPost(allParams, keySort, page, pageSize, sortField));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "admin/getPost/{postId}")
    public ResponseEntity<PostResponse> getPostAdmin(@PathVariable int postId) {
        return response(service.getPrivatePost(postId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "user/{id}/getPost/{postId}")
    public ResponseEntity<PostResponse> getPostUser(@PathVariable int id, @PathVariable int postId) {
        return response(service.getPrivatePost(postId));
    }

    @GetMapping(value = "public/getPost/{postId}")
    public ResponseEntity<PostResponse> getPostPublic(@PathVariable int postId) {
        return response(service.getPostById(postId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "user/{id}/addNewPost")
    public void addNewPost(@PathVariable int id, @RequestBody PostRequest postRequest) {
        service.addNewPost(postRequest, id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "user/{id}/updatePost/{postId}")
    public void updatePost(@PathVariable int id, @PathVariable int postId, @RequestBody PostUpdateReq postUpdateReq) {
        service.updatePostById(postUpdateReq, postId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "admin/deletePost/{postId}")
    public void deletePostAdmin(@PathVariable int postId) {
        service.deletePostById(postId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "user/{id}/deletePost/{postId}")
    public void deletePostUser(@PathVariable int id, @PathVariable int postId) {
        service.deletePostById(postId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "admin/changeEnabled/{postId}")
    public void changeEnabledAdmin(@PathVariable int postId,
            @RequestParam(required = true, defaultValue = "0") int input) {
        service.changeEnabled(input, postId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "user/{id}/changeEnabled/{postId}")
    public void changeEnabledUser(@PathVariable int postId, @PathVariable int id,
            @RequestParam(required = true, defaultValue = "0") int input) {
        service.changeEnabled(input, postId);
    }

}
