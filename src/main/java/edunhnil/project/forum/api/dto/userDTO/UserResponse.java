package edunhnil.project.forum.api.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String role;
    private String created;
    private String modified;
    private int enabled;
    private int deleted;
}
