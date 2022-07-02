package edunhnil.project.forum.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edunhnil.project.forum.api.service.likeService.LikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "like")
@CrossOrigin(origins = "http://localhost:3000/", methods = { RequestMethod.OPTIONS, RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE }, allowedHeaders = "*", allowCredentials = "true")
public class LikeController extends AbstractController<LikeService> {

    @GetMapping(value = "public/getNumberPostLike/{postId}")
    public ResponseEntity<Integer> getNumberPostLike(@PathVariable int postId) {
        return response(service.getTotalPostLike(postId));
    }

    @GetMapping(value = "public/getNumberCommentLike/{commentId}")
    public ResponseEntity<Integer> getNumberCommentLike(@PathVariable int commentId) {
        return response(service.getTotalCommentLike(commentId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "user/{id}/commentStatusLikeBtn/{commentId}")
    public ResponseEntity<Boolean> commentStatusLikeBtn(@PathVariable(name = "id") int ownerId,
            @PathVariable int commentId) {
        return response(service.checkLikeComment(commentId, ownerId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "user/{id}/postStatusLikeBtn/{postId}")
    public ResponseEntity<Boolean> postStatusLikeBtn(@PathVariable(name = "id") int ownerId, @PathVariable int postId) {
        return response(service.checkLikePost(postId, ownerId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "user/{id}/likePost/{postId}")
    public void likePost(@PathVariable(name = "id") int ownerId, @PathVariable int postId) {
        service.addNewPostLike(ownerId, postId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "user/{id}/likeComment/{commentId}")
    public void likeComment(@PathVariable(name = "id") int ownerId, @PathVariable int commentId) {
        service.addNewCommentLike(ownerId, commentId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "user/{id}/discardLikeComment/{commentId}")
    public void discardLikeComment(@PathVariable(name = "id") int ownerId, @PathVariable int commentId) {
        service.hideCommentLike(ownerId, commentId);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "user/{id}/discardLikePost/{postId}")
    public void discardLikePost(@PathVariable(name = "id") int ownerId, @PathVariable int postId) {
        service.hideLikePost(ownerId, postId);
    }
}
