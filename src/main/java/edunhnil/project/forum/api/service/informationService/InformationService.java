package edunhnil.project.forum.api.service.informationService;

import java.util.Map;
import java.util.Optional;

import edunhnil.project.forum.api.dto.informationDTO.InformationRequest;
import edunhnil.project.forum.api.dto.informationDTO.InformationResponse;
import edunhnil.project.forum.api.dto.informationDTO.InformationUpdateReq;
import edunhnil.project.forum.api.dto.informationDTO.ListInformationResponse;

public interface InformationService {
        Optional<ListInformationResponse> getAllInformation(Map<String, String> allParams, String keySort, int page,
                        int pageSize, String sortField);

        Optional<ListInformationResponse> getAllPublicInformation(Map<String, String> allParams, String keySort,
                        int page,
                        int pageSize, String sortField);

        Optional<InformationResponse> getInformationByUserCode(int id, int deleted);

        Optional<InformationResponse> getInformationPublic(int id);

        void addNewInformation(InformationRequest informationRequest);

        void updateInformationByUserCode(InformationUpdateReq informationRequest, int id);

}
