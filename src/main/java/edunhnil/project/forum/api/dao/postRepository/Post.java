package edunhnil.project.forum.api.dao.postRepository;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private int authorId;
    private String title;
    private String content;
    private int view;
    private int categoryId;
    private Date created;
    private Date modified;
    private int enabled;
    private int deleted;
}
