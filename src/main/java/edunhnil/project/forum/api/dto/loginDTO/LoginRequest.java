package edunhnil.project.forum.api.dto.loginDTO;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "username is required")
    private String username;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "password is required")
    private String password;
}