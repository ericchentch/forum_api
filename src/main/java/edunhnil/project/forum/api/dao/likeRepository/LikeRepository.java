package edunhnil.project.forum.api.dao.likeRepository;

public interface LikeRepository {
    int getTotalPostLike(int postId);

    int getTotalCommentLike(int commentId);

    boolean checkLikeComment(int commentId, int ownerId);

    boolean checkLikePost(int postId, int ownerId);

    void addNewCommentLike(int ownerId, int commentId);

    void addNewPostId(int ownerId, int postId);

    void hideLikePost(int ownerId, int postId);

    void hideCommentLike(int ownerId, int commentId);
}
