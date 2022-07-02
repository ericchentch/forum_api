package edunhnil.project.forum.api.dto.loginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(type = "string", example = " ")
    private String username;

    @Schema(type = "string", example = " ")
    private String password;

    @Schema(type = "string", example = " ")
    private String firstName;

    @Schema(type = "string", example = " ")
    private String lastName;

    @Schema(type = "int", example = "0")
    private int gender;

    @Schema(type = "string", example = "yyyy-mm-dd")
    private String dob;

    @Schema(type = "string", example = " ")
    private String phone;

    @Schema(type = "string", example = " ")
    private String email;

    @Schema(type = "string", example = " ")
    private String address;

}
