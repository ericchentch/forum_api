package edunhnil.project.forum.api.dto.userDTO;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordReq {

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "old password is required")
    private String oldPassword;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "new password is required")
    private String newPassword;
}
