package edunhnil.project.forum.api.dao.userRepository;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private int enabled;
    private Date created;
    private Date modified;
    private int deleted;
}
