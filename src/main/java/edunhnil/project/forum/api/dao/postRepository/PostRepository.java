package edunhnil.project.forum.api.dao.postRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostRepository {
        Optional<List<Post>> getPostsByAuthorId(Map<String, String> allParams, String keySort, int page, int pageSize,
                        String sortField);

        Optional<Post> getPostById(int id);

        Optional<Post> getPrivatePostById(int id);

        void updatePostById(Post post, int id);

        void addNewPost(Post post);

        void deletePostById(int id);

        void changeEnabled(int input, int id);

        void oneMoreView(int id);

        int getTotalPage(Map<String, String> allParams);

        void resetId();
}
