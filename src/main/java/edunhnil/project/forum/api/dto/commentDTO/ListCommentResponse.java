package edunhnil.project.forum.api.dto.commentDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCommentResponse {
    private List<CommentResponse> data;
    private int page;
    private int pageSize;
    private int totalRows;
}
