package edunhnil.project.forum.api.service.likeService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edunhnil.project.forum.api.dao.commentRepository.CommentRepository;
import edunhnil.project.forum.api.dao.likeRepository.LikeRepository;
import edunhnil.project.forum.api.dao.postRepository.PostRepository;
import edunhnil.project.forum.api.dao.userRepository.UserRepository;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.service.AbstractService;

@Service
public class LikeServiceImpl extends AbstractService<LikeRepository> implements LikeService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    void checkPostId(int postId) {
        postRepository.getPostById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + postId));
    }

    void checkCommentId(int commentId) {
        commentRepository.getCommentById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found comment with id: " + commentId));
    }

    void checkOwnerId(int ownerId) {
        userRepository.getUserById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + ownerId));
    }

    @Override
    public Optional<Integer> getTotalPostLike(int postId) {
        checkPostId(postId);
        return Optional.of(repository.getTotalPostLike(postId));
    }

    @Override
    public Optional<Integer> getTotalCommentLike(int commentId) {
        checkCommentId(commentId);
        return Optional.of(repository.getTotalCommentLike(commentId));
    }

    @Override
    public Optional<Boolean> checkLikeComment(int commentId, int ownerId) {
        checkCommentId(commentId);
        checkOwnerId(ownerId);
        return Optional.of(repository.checkLikeComment(commentId, ownerId));
    }

    @Override
    public Optional<Boolean> checkLikePost(int postId, int ownerId) {
        checkPostId(postId);
        checkOwnerId(ownerId);
        return Optional.of(repository.checkLikePost(postId, ownerId));
    }

    @Override
    public void addNewCommentLike(int ownerId, int commentId) {
        checkOwnerId(ownerId);
        checkCommentId(commentId);
        if (!repository.checkLikeComment(commentId, ownerId)) {
            repository.addNewCommentLike(ownerId, commentId);
        }
    }

    @Override
    public void addNewPostLike(int ownerId, int postId) {
        checkOwnerId(ownerId);
        checkPostId(postId);
        if (!repository.checkLikePost(postId, ownerId)) {
            repository.addNewPostId(ownerId, postId);
        }
    }

    @Override
    public void hideLikePost(int ownerId, int postId) {
        checkOwnerId(ownerId);
        checkPostId(postId);
        if (repository.checkLikePost(postId, ownerId)) {
            repository.hideLikePost(ownerId, postId);
        }
    }

    @Override
    public void hideCommentLike(int ownerId, int commentId) {
        checkOwnerId(ownerId);
        checkCommentId(commentId);
        if (repository.checkLikeComment(commentId, ownerId)) {
            repository.hideCommentLike(ownerId, commentId);
        }
    }

}
