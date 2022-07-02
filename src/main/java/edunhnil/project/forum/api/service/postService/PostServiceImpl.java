package edunhnil.project.forum.api.service.postService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edunhnil.project.forum.api.constant.DateTime;
import edunhnil.project.forum.api.dao.categoryRepository.CategoryRepository;
import edunhnil.project.forum.api.dao.informationRepository.Information;
import edunhnil.project.forum.api.dao.informationRepository.InformationRepository;
import edunhnil.project.forum.api.dao.likeRepository.LikeRepository;
import edunhnil.project.forum.api.dao.postRepository.Post;
import edunhnil.project.forum.api.dao.postRepository.PostRepository;
import edunhnil.project.forum.api.dto.categoryDTO.CategoryResponse;
import edunhnil.project.forum.api.dto.informationDTO.InformationResponse;
import edunhnil.project.forum.api.dto.postDTO.ListPostResponse;
import edunhnil.project.forum.api.dto.postDTO.PostRequest;
import edunhnil.project.forum.api.dto.postDTO.PostResponse;
import edunhnil.project.forum.api.dto.postDTO.PostUpdateReq;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.service.AbstractService;
import edunhnil.project.forum.api.ultilities.DateFormat;

@Service
public class PostServiceImpl extends AbstractService<PostRepository> implements PostService {

        @Autowired
        private InformationRepository informationRepository;

        @Autowired
        private LikeRepository likeRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Override
        public Optional<ListPostResponse> getPublicPost(Map<String, String> allParams, String keySort, int page,
                        int pageSize, String sortField) {
                if (allParams.containsKey("deleted")) {
                        allParams.replace("deleted", "0");
                } else {
                        allParams.put("deleted", "0");
                }
                if (allParams.containsKey("enabled")) {
                        allParams.replace("enabled", "0");
                } else {
                        allParams.put("enabled", "0");
                }
                if (allParams.containsKey("authorId")) {
                        allParams.remove("authorId");
                }
                List<Post> posts = repository
                                .getPostsByAuthorId(allParams, keySort, page, pageSize,
                                                sortField)
                                .orElseThrow(() -> new ResourceNotFoundException("No post"));

                return Optional.of(new ListPostResponse(
                                posts.stream()
                                                .map(p -> {
                                                        Information author = informationRepository
                                                                        .getInformationByUserCode(p.getAuthorId(), 0)
                                                                        .get();
                                                        return new PostResponse(p.getId(), p.getAuthorId(),
                                                                        new InformationResponse(author.getId(),
                                                                                        author.getUserId(),
                                                                                        author.getFirstName(),
                                                                                        author.getLastName(),
                                                                                        author.getGender(),
                                                                                        DateFormat.toDateString(
                                                                                                        author.getDob(),
                                                                                                        DateTime.YYYY_MM_DD),
                                                                                        "",
                                                                                        "",
                                                                                        "",
                                                                                        "",
                                                                                        ""),
                                                                        p.getTitle(),
                                                                        p.getContent(),
                                                                        p.getView(),
                                                                        likeRepository.getTotalPostLike(p.getId()),
                                                                        p.getCategoryId(),
                                                                        objectMapper.convertValue(
                                                                                        categoryRepository
                                                                                                        .getCategoryById(
                                                                                                                        p.getCategoryId())
                                                                                                        .get(),
                                                                                        CategoryResponse.class),
                                                                        DateFormat.toDateString(p.getCreated(),
                                                                                        DateTime.YYYY_MM_DD),
                                                                        DateFormat.toDateString(p.getModified(),
                                                                                        DateTime.YYYY_MM_DD),
                                                                        p.getEnabled(),
                                                                        p.getDeleted());
                                                })
                                                .collect(Collectors.toList()),
                                page, pageSize,
                                repository.getTotalPage(allParams)));
        }

        @Override
        public Optional<ListPostResponse> getPostsByAuthorId(Map<String, String> allParams, String keySort, int page,
                        int pageSize, String sortField, int authorId) {
                if (allParams.containsKey("authorId")) {
                        allParams.replace("authorId", Integer.toString(authorId));

                } else {
                        allParams.put("authorId", Integer.toString(authorId));

                }
                List<Post> posts = repository
                                .getPostsByAuthorId(allParams, keySort, page, pageSize,
                                                sortField)
                                .orElseThrow(() -> new ResourceNotFoundException("No post"));

                return Optional.of(new ListPostResponse(
                                posts.stream()
                                                .map(p -> {
                                                        Information author = informationRepository
                                                                        .getInformationByUserCode(p.getAuthorId(), 0)
                                                                        .get();
                                                        return new PostResponse(p.getId(), p.getAuthorId(),
                                                                        new InformationResponse(author.getId(),
                                                                                        author.getUserId(),
                                                                                        author.getFirstName(),
                                                                                        author.getLastName(),
                                                                                        author.getGender(),
                                                                                        DateFormat.toDateString(
                                                                                                        author.getDob(),
                                                                                                        DateTime.YYYY_MM_DD),
                                                                                        author.getAddress(),
                                                                                        author.getEmail(),
                                                                                        author.getPhone(),
                                                                                        DateFormat.toDateString(
                                                                                                        author.getCreated(),
                                                                                                        DateTime.YYYY_MM_DD),
                                                                                        DateFormat.toDateString(
                                                                                                        author.getModified(),
                                                                                                        DateTime.YYYY_MM_DD)),
                                                                        p.getTitle(),
                                                                        p.getContent(),
                                                                        p.getView(),
                                                                        likeRepository.getTotalPostLike(p.getId()),
                                                                        p.getCategoryId(),
                                                                        objectMapper.convertValue(
                                                                                        categoryRepository
                                                                                                        .getCategoryById(
                                                                                                                        p.getCategoryId())
                                                                                                        .get(),
                                                                                        CategoryResponse.class),
                                                                        DateFormat.toDateString(p.getCreated(),
                                                                                        DateTime.YYYY_MM_DD),
                                                                        DateFormat.toDateString(p.getModified(),
                                                                                        DateTime.YYYY_MM_DD),
                                                                        p.getEnabled(),
                                                                        p.getDeleted());
                                                })
                                                .collect(Collectors.toList()),
                                page, pageSize, repository.getTotalPage(allParams)));
        }

        @Override
        public Optional<PostResponse> getPrivatePost(int id) {
                Post post = repository.getPrivatePostById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
                Information author = informationRepository.getInformationByUserCode(post.getAuthorId(), 0)
                                .get();
                return Optional.of(new PostResponse(post.getId(), post.getAuthorId(),
                                new InformationResponse(author.getId(), author.getUserId(), author.getFirstName(),
                                                author.getLastName(),
                                                author.getGender(),
                                                DateFormat.toDateString(author.getDob(), DateTime.YYYY_MM_DD),
                                                author.getAddress(), author.getEmail(), author.getPhone(),
                                                DateFormat.toDateString(author.getCreated(), DateTime.YYYY_MM_DD),
                                                DateFormat.toDateString(author.getModified(), DateTime.YYYY_MM_DD)),
                                post.getTitle(),
                                post.getContent(), post.getView(), likeRepository.getTotalPostLike(post.getId()),
                                post.getCategoryId(),
                                objectMapper.convertValue(
                                                categoryRepository.getCategoryById(post.getCategoryId()).get(),
                                                CategoryResponse.class),
                                DateFormat.toDateString(post.getCreated(), DateTime.YYYY_MM_DD),
                                DateFormat.toDateString(post.getModified(), DateTime.YYYY_MM_DD), post.getEnabled(),
                                post.getDeleted()));
        }

        @Override
        public Optional<PostResponse> getPostById(int id) {
                Post post = repository.getPostById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
                repository.oneMoreView(id);
                Information author = informationRepository.getInformationByUserCode(post.getAuthorId(), 0)
                                .get();
                return Optional.of(new PostResponse(post.getId(), post.getAuthorId(),
                                new InformationResponse(author.getId(), author.getUserId(), author.getFirstName(),
                                                author.getLastName(), author.getGender(),
                                                DateFormat.toDateString(author.getDob(), DateTime.YYYY_MM_DD), "", "",
                                                "", "", ""),
                                post.getTitle(),
                                post.getContent(), post.getView(), likeRepository.getTotalPostLike(post.getId()),
                                post.getCategoryId(),
                                objectMapper.convertValue(
                                                categoryRepository.getCategoryById(post.getCategoryId()).get(),
                                                CategoryResponse.class),
                                DateFormat.toDateString(post.getCreated(), DateTime.YYYY_MM_DD),
                                DateFormat.toDateString(post.getModified(), DateTime.YYYY_MM_DD), 0, 0));
        }

        @Override
        public void updatePostById(PostUpdateReq req, int id) {
                repository.getPrivatePostById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
                validate(req);
                repository.updatePostById(objectMapper.convertValue(req, Post.class), id);

        }

        @Override
        public void addNewPost(PostRequest postRequest, int authorId) {
                validate(postRequest);
                postRequest.setAuthorId(authorId);
                repository.resetId();
                repository.addNewPost(objectMapper.convertValue(postRequest, Post.class));
        }

        @Override
        public void deletePostById(int id) {
                repository.getPrivatePostById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
                repository.deletePostById(id);
        }

        @Override
        public void changeEnabled(int input, int id) {
                repository.getPrivatePostById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
                repository.changeEnabled(input, id);

        }

}
