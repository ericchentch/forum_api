package edunhnil.project.forum.api.dao.informationRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InformationRepository {
    Optional<List<Information>> getAllInformation(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField);

    Optional<Information> getInformationByUserCode(int id, int deleted);

    void addNewInformation(Information information);

    void updateInformationByUserCode(Information information, int id);

    int getTotalPage(Map<String, String> allParams);

    void resetId();
}
