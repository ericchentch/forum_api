package edunhnil.project.forum.api.service.likeService;

import java.util.Optional;

public interface LikeService {
    Optional<Integer> getTotalPostLike(int postId);

    Optional<Integer> getTotalCommentLike(int commentId);

    Optional<Boolean> checkLikeComment(int commentId, int ownerId);

    Optional<Boolean> checkLikePost(int postId, int ownerId);

    void addNewCommentLike(int ownerId, int commentId);

    void addNewPostLike(int ownerId, int postId);

    void hideLikePost(int ownerId, int postId);

    void hideCommentLike(int ownerId, int commentId);
}
