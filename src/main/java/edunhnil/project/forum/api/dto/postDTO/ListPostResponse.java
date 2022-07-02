package edunhnil.project.forum.api.dto.postDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPostResponse {
    private List<PostResponse> data;
    private int page;
    private int pageSize;
    private int totalRows;
}
