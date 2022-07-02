package edunhnil.project.forum.api.service.commentService;

import java.util.Map;
import java.util.Optional;

import edunhnil.project.forum.api.dto.commentDTO.CommentRequest;
import edunhnil.project.forum.api.dto.commentDTO.ListCommentResponse;

public interface CommentService {
    Optional<ListCommentResponse> getPublicComment(int postId, int page, String keySort, String sortField);

    Optional<ListCommentResponse> getAdminComment(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField);

    void addNewComment(CommentRequest commentRequest, int postId, int ownerId);

    void editCommentById(CommentRequest commentRequest, int id);

    void deleteComment(int id);
}
