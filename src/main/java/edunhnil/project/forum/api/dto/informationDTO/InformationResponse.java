package edunhnil.project.forum.api.dto.informationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationResponse {
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private int gender;
    private String dob;
    private String address;
    private String email;
    private String phone;
    private String created;
    private String modified;
}
