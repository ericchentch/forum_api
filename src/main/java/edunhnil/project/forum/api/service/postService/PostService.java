package edunhnil.project.forum.api.service.postService;

import java.util.Map;
import java.util.Optional;

import edunhnil.project.forum.api.dto.postDTO.ListPostResponse;
import edunhnil.project.forum.api.dto.postDTO.PostRequest;
import edunhnil.project.forum.api.dto.postDTO.PostResponse;
import edunhnil.project.forum.api.dto.postDTO.PostUpdateReq;

public interface PostService {
        Optional<ListPostResponse> getPostsByAuthorId(Map<String, String> allParams, String keySort, int page,
                        int pageSize, String sortField, int authorId);

        Optional<ListPostResponse> getPublicPost(Map<String, String> allParams, String keySort, int page,
                        int pageSize, String sortField);

        Optional<PostResponse> getPrivatePost(int id);

        Optional<PostResponse> getPostById(int id);

        void updatePostById(PostUpdateReq req, int id);

        void addNewPost(PostRequest postRequest, int authorId);

        void deletePostById(int id);

        void changeEnabled(int input, int id);

}
