package edunhnil.project.forum.api.dto.informationDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListInformationResponse {
    private List<InformationResponse> data;
    private int page;
    private int pageSize;
    private int totalRows;
}
