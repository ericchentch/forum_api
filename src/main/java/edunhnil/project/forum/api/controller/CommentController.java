package edunhnil.project.forum.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edunhnil.project.forum.api.dto.commentDTO.CommentRequest;
import edunhnil.project.forum.api.dto.commentDTO.ListCommentResponse;
import edunhnil.project.forum.api.service.commentService.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "comment")
@CrossOrigin(origins = "http://localhost:3000/", methods = { RequestMethod.OPTIONS, RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE }, allowedHeaders = "*", allowCredentials = "true")

public class CommentController extends AbstractController<CommentService> {

    @GetMapping(value = "/public/getCommentInPost")
    public ResponseEntity<ListCommentResponse> getCommentInPost(
            @RequestParam(required = true, defaultValue = "1") int postId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(defaultValue = "modified") String sortField,
            @RequestParam(defaultValue = "asc") String keySort) {
        return response(service.getPublicComment(postId, page, keySort, sortField));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "admin/getAllComment")
    public ResponseEntity<ListCommentResponse> getCommentAdmin(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String keySort,
            @RequestParam(defaultValue = "modified") String sortField,
            @RequestParam Map<String, String> allParams) {
        return response(service.getAdminComment(allParams, keySort, page, pageSize, sortField));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "user/{id}/addNewComment/{postId}")
    public void addNewComment(@RequestBody CommentRequest commentRequest, @PathVariable(name = "id") int ownerId,
            @PathVariable int postId) {
        service.addNewComment(commentRequest, postId, ownerId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "user/{id}/editComment/{commentId}")
    public void editComment(@RequestBody CommentRequest commentRequest, @PathVariable int id,
            @PathVariable int commentId) {
        service.editCommentById(commentRequest, id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "user/{id}/deleteComment/{commentId}")
    public void userDeleteComment(@PathVariable int id, @PathVariable int commentId) {
        service.deleteComment(commentId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "admin/deleteComment/{commentId}")
    public void adminDeleteComment(@PathVariable int commentId) {
        service.deleteComment(commentId);
    }
}
