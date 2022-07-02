package edunhnil.project.forum.api.dao.likeRepository;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {
    private int id;
    private int ownerId;
    private int postId;
    private Date created;
}
