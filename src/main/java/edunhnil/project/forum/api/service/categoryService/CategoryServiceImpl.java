package edunhnil.project.forum.api.service.categoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edunhnil.project.forum.api.constant.DateTime;
import edunhnil.project.forum.api.dao.categoryRepository.Category;
import edunhnil.project.forum.api.dao.categoryRepository.CategoryRepository;
import edunhnil.project.forum.api.dao.informationRepository.Information;
import edunhnil.project.forum.api.dao.informationRepository.InformationRepository;
import edunhnil.project.forum.api.dao.likeRepository.LikeRepository;
import edunhnil.project.forum.api.dao.postRepository.Post;
import edunhnil.project.forum.api.dao.postRepository.PostRepository;
import edunhnil.project.forum.api.dto.categoryDTO.CategoryResponse;
import edunhnil.project.forum.api.dto.informationDTO.InformationResponse;
import edunhnil.project.forum.api.dto.postDTO.PostResponse;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.service.AbstractService;
import edunhnil.project.forum.api.ultilities.DateFormat;

@Service
public class CategoryServiceImpl extends AbstractService<CategoryRepository> implements CategoryService {

        @Autowired
        private PostRepository postRepository;

        @Autowired
        private InformationRepository informationRepository;

        @Autowired
        private LikeRepository likeRepository;

        @Override
        public Optional<List<CategoryResponse>> getCategories() {
                List<Category> categories = repository.getCategories()
                                .orElseThrow(() -> new ResourceNotFoundException("not found"));
                List<CategoryResponse> result = new ArrayList<>();
                for (Category c : categories) {
                        Map<String, String> allParams = new HashMap<String, String>();
                        allParams.put("categoryId", Integer.toString(c.getId()));
                        allParams.put("deleted", "0");
                        allParams.put("enabled", "0");
                        List<Post> posts = postRepository
                                        .getPostsByAuthorId(allParams, "DESC", 1, 10, "created")
                                        .orElseThrow(() -> new ResourceNotFoundException("Not found any post"));
                        if (posts.size() != 0) {
                                Information authorInformation = informationRepository
                                                .getInformationByUserCode(posts.get(0).getAuthorId(), 0)
                                                .orElseThrow(() -> new ResourceNotFoundException(
                                                                "Not found author with: "
                                                                                + posts.get(0).getAuthorId()));
                                PostResponse newestPost = new PostResponse(posts.get(0).getId(),
                                                posts.get(0).getAuthorId(),
                                                new InformationResponse(authorInformation.getId(),
                                                                authorInformation.getUserId(),
                                                                authorInformation.getFirstName(),
                                                                authorInformation.getLastName(),
                                                                authorInformation.getGender(),
                                                                DateFormat.toDateString(authorInformation.getDob(),
                                                                                DateTime.YYYY_MM_DD),
                                                                "", "", "", "", ""),
                                                posts.get(0).getTitle(), posts.get(0).getContent(),
                                                posts.get(0).getView(),
                                                likeRepository.getTotalPostLike(posts.get(0).getId()),
                                                posts.get(0).getAuthorId(), null,
                                                DateFormat.toDateString(posts.get(0).getCreated(), DateTime.YYYY_MM_DD),
                                                DateFormat.toDateString(posts.get(0).getModified(),
                                                                DateTime.YYYY_MM_DD),
                                                0, 0);
                                result.add(new CategoryResponse(c.getId(), c.getCategoryName(), c.getPath(),
                                                posts.size(), newestPost));
                        } else {
                                result.add(new CategoryResponse(c.getId(), c.getCategoryName(), c.getPath(),
                                                posts.size(), null));
                        }
                }
                return Optional
                                .of(result);
        }

        @Override
        public Optional<CategoryResponse> getCategoryById(int id) {
                Category category = repository.getCategoryById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found category with id: " + id));
                List<String> searchField = new ArrayList<>();
                searchField.add("category_id");
                Map<String, String> allParams = new HashMap<String, String>();
                allParams.put("category_id", Integer.toString(category.getId()));
                allParams.put("deleted", "0");
                allParams.put("enabled", "0");
                List<Post> posts = postRepository.getPostsByAuthorId(allParams, "DESC", 1, 10, "created")
                                .get();
                if (posts.size() != 0) {
                        Information authorInformation = informationRepository
                                        .getInformationByUserCode(posts.get(0).getAuthorId(), 0)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Not found author with: " + posts.get(0).getAuthorId()));
                        PostResponse newestPost = new PostResponse(posts.get(0).getId(), posts.get(0).getAuthorId(),
                                        new InformationResponse(authorInformation.getId(),
                                                        authorInformation.getUserId(),
                                                        authorInformation.getFirstName(),
                                                        authorInformation.getLastName(), authorInformation.getGender(),
                                                        DateFormat.toDateString(authorInformation.getDob(),
                                                                        DateTime.YYYY_MM_DD),
                                                        "", "", "", "", ""),
                                        posts.get(0).getTitle(), posts.get(0).getContent(), posts.get(0).getView(),
                                        likeRepository.getTotalPostLike(posts.get(0).getId()),
                                        posts.get(0).getAuthorId(), null,
                                        DateFormat.toDateString(posts.get(0).getCreated(), DateTime.YYYY_MM_DD),
                                        DateFormat.toDateString(posts.get(0).getModified(), DateTime.YYYY_MM_DD), 0, 0);
                        return Optional.of(new CategoryResponse(category.getId(), category.getCategoryName(),
                                        category.getPath(),
                                        posts.size(),
                                        newestPost));

                } else {
                        return Optional.of(new CategoryResponse(category.getId(), category.getCategoryName(),
                                        category.getPath(),
                                        posts.size(), null));
                }
        }

}
