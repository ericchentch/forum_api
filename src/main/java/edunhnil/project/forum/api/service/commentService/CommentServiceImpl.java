package edunhnil.project.forum.api.service.commentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edunhnil.project.forum.api.constant.DateTime;
import edunhnil.project.forum.api.dao.commentRepository.Comment;
import edunhnil.project.forum.api.dao.commentRepository.CommentRepository;
import edunhnil.project.forum.api.dao.informationRepository.Information;
import edunhnil.project.forum.api.dao.informationRepository.InformationRepository;
import edunhnil.project.forum.api.dao.likeRepository.LikeRepository;
import edunhnil.project.forum.api.dao.postRepository.PostRepository;
import edunhnil.project.forum.api.dto.commentDTO.CommentRequest;
import edunhnil.project.forum.api.dto.commentDTO.CommentResponse;
import edunhnil.project.forum.api.dto.commentDTO.ListCommentResponse;
import edunhnil.project.forum.api.dto.informationDTO.InformationResponse;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.service.AbstractService;
import edunhnil.project.forum.api.ultilities.DateFormat;

@Service
public class CommentServiceImpl extends AbstractService<CommentRepository> implements CommentService {

        @Autowired
        private PostRepository postRepository;

        @Autowired
        private InformationRepository informationRepository;

        @Autowired
        LikeRepository likeRepository;

        @Override
        public Optional<ListCommentResponse> getPublicComment(int postId, int page, String keySort, String sortField) {
                Map<String, String> allParams = new HashMap<String, String>();
                allParams.put("postId", Integer.toString(postId));
                List<Comment> comments = repository
                                .getAllComment(allParams, keySort, page, 5, sortField)
                                .orElseThrow(() -> new ResourceNotFoundException("No Comment"));
                return Optional.of(new ListCommentResponse(comments.stream()
                                .map(c -> {
                                        Information ownerInformation = informationRepository
                                                        .getInformationByUserCode(c.getOwnerId(), 0)
                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                        "Not found information of owner."));
                                        return new CommentResponse(c.getId(), c.getOwnerId(),
                                                        new InformationResponse(ownerInformation.getId(),
                                                                        ownerInformation.getUserId(),
                                                                        ownerInformation.getFirstName(),
                                                                        ownerInformation.getLastName(),
                                                                        ownerInformation.getGender(),
                                                                        DateFormat.toDateString(
                                                                                        ownerInformation.getDob(),
                                                                                        DateTime.YYYY_MM_DD),
                                                                        "", "", "", "", ""),
                                                        c.getPostId(),
                                                        c.getContent(), likeRepository.getTotalCommentLike(c.getId()),
                                                        DateFormat.toDateString(c.getCreated(), DateTime.YYYY_MM_DD),
                                                        DateFormat.toDateString(c.getModified(), DateTime.YYYY_MM_DD),
                                                        0);
                                })
                                .collect(Collectors.toList()), page, 5, repository.getTotalCommentPost(postId)));
        }

        @Override
        public Optional<ListCommentResponse> getAdminComment(Map<String, String> allParams, String keySort, int page,
                        int pageSize,
                        String sortField) {
                List<Comment> comments = repository
                                .getAllComment(allParams, keySort, page, pageSize, sortField)
                                .orElseThrow(() -> new ResourceNotFoundException("No comment"));
                return Optional
                                .of(new ListCommentResponse(
                                                comments.stream()
                                                                .map(c -> {
                                                                        Information ownerInformation = informationRepository
                                                                                        .getInformationByUserCode(
                                                                                                        c.getOwnerId(),
                                                                                                        0)
                                                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                                                        "Not found information of owner."));
                                                                        return new CommentResponse(c.getId(),
                                                                                        c.getOwnerId(),
                                                                                        new InformationResponse(
                                                                                                        ownerInformation.getId(),
                                                                                                        ownerInformation.getUserId(),
                                                                                                        ownerInformation.getFirstName(),
                                                                                                        ownerInformation.getLastName(),
                                                                                                        ownerInformation.getGender(),
                                                                                                        DateFormat.toDateString(
                                                                                                                        ownerInformation.getDob(),
                                                                                                                        DateTime.YYYY_MM_DD),
                                                                                                        ownerInformation.getAddress(),
                                                                                                        ownerInformation.getEmail(),
                                                                                                        ownerInformation.getPhone(),
                                                                                                        DateFormat.toDateString(
                                                                                                                        ownerInformation.getCreated(),
                                                                                                                        DateTime.YYYY_MM_DD),
                                                                                                        DateFormat.toDateString(
                                                                                                                        ownerInformation.getModified(),
                                                                                                                        DateTime.YYYY_MM_DD)),
                                                                                        c.getPostId(),
                                                                                        c.getContent(),
                                                                                        likeRepository.getTotalCommentLike(
                                                                                                        c.getId()),
                                                                                        DateFormat.toDateString(
                                                                                                        c.getCreated(),
                                                                                                        DateTime.YYYY_MM_DD),
                                                                                        DateFormat.toDateString(
                                                                                                        c.getModified(),
                                                                                                        DateTime.YYYY_MM_DD),
                                                                                        c.getDeleted());
                                                                })
                                                                .collect(Collectors.toList()),
                                                page, pageSize,
                                                repository.getTotalCommentAdmin(allParams)));
        }

        @Override
        public void addNewComment(CommentRequest commentRequest, int postId, int ownerId) {
                validate(commentRequest);
                repository.resetId();
                postRepository.getPostById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + postId));
                Comment comment = objectMapper.convertValue(commentRequest, Comment.class);
                comment.setPostId(postId);
                comment.setOwnerId(ownerId);
                repository.addNewComment(comment);
        }

        @Override
        public void editCommentById(CommentRequest commentRequest, int id) {
                validate(commentRequest);
                repository.getCommentById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found comment with id: " + id));
                repository.editCommentById(objectMapper.convertValue(commentRequest, Comment.class), id);
        }

        @Override
        public void deleteComment(int id) {
                repository.getCommentById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found comment with id: " + id));
                repository.deleteCommentById(id);
        }

}
