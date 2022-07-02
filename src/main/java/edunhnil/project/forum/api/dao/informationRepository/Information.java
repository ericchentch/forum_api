package edunhnil.project.forum.api.dao.informationRepository;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    private int id;
    private int userId;
    private int gender;
    private Date dob;
    private String address;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date created;
    private Date modified;
}
