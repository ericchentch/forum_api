package edunhnil.project.forum.api.dto.commentDTO;

import edunhnil.project.forum.api.dto.informationDTO.InformationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private int id;
    private int ownerId;
    private InformationResponse informationOwner;
    private int postId;
    private String content;
    private int like;
    private String created;
    private String modified;
    private int deleted;
}
