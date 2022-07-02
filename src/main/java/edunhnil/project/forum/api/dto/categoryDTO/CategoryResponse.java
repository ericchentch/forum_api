package edunhnil.project.forum.api.dto.categoryDTO;

import edunhnil.project.forum.api.dto.postDTO.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private int id;
    private String categoryName;
    private String path;
    private int postsNumber;
    private PostResponse newestPost;
}
