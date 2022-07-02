package edunhnil.project.forum.api.dto.postDTO;

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
public class PostRequest {
    @Schema(type = "int", example = "0")
    @Min(value = 0)
    @NotNull(message = "author id is required")
    private int authorId;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "title is required")
    private String title;

    @Schema(type = "string", example = " ")
    @NotEmpty(message = "content is required")
    private String content;

    @Schema(type = "int", example = "0")
    @Min(value = 0)
    @NotNull(message = "category id is required")
    private int categoryId;

}
