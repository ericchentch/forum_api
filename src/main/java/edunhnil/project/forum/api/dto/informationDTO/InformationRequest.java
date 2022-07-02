package edunhnil.project.forum.api.dto.informationDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationRequest {

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "First name is required")
    private String firstName;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @Schema(type = "int", example = "0")
    @NotNull(message = "user id is required")
    private int userId;

    @Schema(type = "int", example = "0")
    @Min(value = 0, message = "gender must be 0 or 1")
    @Max(value = 1, message = "gender must be 0 or 1")
    @NotNull(message = "gender is required")
    private int gender;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "Date of birth is required")
    private String dob;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "Address is required")
    private String address;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "Email is required")
    private String email;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "Phone is required")
    private String phone;
}
