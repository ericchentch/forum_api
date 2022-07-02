package edunhnil.project.forum.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import edunhnil.project.forum.api.dao.commentRepository.Comment;
import edunhnil.project.forum.api.dao.commentRepository.CommentRepository;
import edunhnil.project.forum.api.dao.postRepository.Post;
import edunhnil.project.forum.api.dao.postRepository.PostRepository;
import edunhnil.project.forum.api.dao.userRepository.CustomUserDetail;
import edunhnil.project.forum.api.dao.userRepository.User;
import edunhnil.project.forum.api.dao.userRepository.UserRepository;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;

@Component
public class Guard {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PostRepository postRepository;

        @Autowired
        private CommentRepository commentRepository;

        public boolean checkUserId(Authentication authentication, int id) {
                if (authentication.getPrincipal().equals("anonymousUser"))
                        return false;
                CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
                String username = userDetail.getUsername();
                User user = userRepository.getUserByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found user id: " + id));
                return user != null && user.getId() == id;
        }

        public boolean checkAuthorId(Authentication authentication, int id, int postId) {
                if (authentication.getPrincipal().equals("anonymousUser"))
                        return false;
                CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
                String username = userDetail.getUsername();
                User user = userRepository.getUserByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found user id: " + id));
                Post post = postRepository.getPrivatePostById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found post id: " + postId));
                return user != null && user.getId() == id && post.getAuthorId() == id;
        }

        public boolean checkCommentId(Authentication authentication, int id, int commentId) {
                if (authentication.getPrincipal().equals("anonymousUser"))
                        return false;
                CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
                String username = userDetail.getUsername();
                User user = userRepository.getUserByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found user id: " + id));
                Comment comment = commentRepository.getCommentById(commentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Not found comment with id: " + commentId));
                return user != null && user.getId() == id && comment.getOwnerId() == id;
        }

}
