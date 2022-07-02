package edunhnil.project.forum.api.dto.userDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUserResponse {
    private List<UserResponse> data;
    private int page;
    private int pageSize;
    private int totalPage;
}
