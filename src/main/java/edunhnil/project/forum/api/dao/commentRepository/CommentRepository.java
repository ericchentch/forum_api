package edunhnil.project.forum.api.dao.commentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentRepository {
    Optional<List<Comment>> getAllComment(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField);

    Optional<Comment> getCommentById(int id);

    void addNewComment(Comment comment);

    void editCommentById(Comment comment, int id);

    void deleteCommentById(int id);

    void resetId();

    int getTotalCommentPost(int postId);

    int getTotalCommentAdmin(Map<String, String> allParams);
}
