package edunhnil.project.forum.api.dto.userDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class UserRequest {
    @Schema(type = "string", example = " ")
    @NotEmpty(message = "username is required!")
    private String username;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "password is required!")
    private String password;

    @Schema(type = "string", example = "ROLE_USER")
    @NotEmpty(message = "role is required!")
    private String role;

    @Min(value = 0)
    @Max(value = 1)
    @Schema(type = "int", example = "0")
    private int enabled;

}
