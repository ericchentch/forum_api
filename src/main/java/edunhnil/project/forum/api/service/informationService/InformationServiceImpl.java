package edunhnil.project.forum.api.service.informationService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edunhnil.project.forum.api.constant.DateTime;
import edunhnil.project.forum.api.dao.informationRepository.Information;
import edunhnil.project.forum.api.dao.informationRepository.InformationRepository;
import edunhnil.project.forum.api.dto.informationDTO.InformationRequest;
import edunhnil.project.forum.api.dto.informationDTO.InformationResponse;
import edunhnil.project.forum.api.dto.informationDTO.InformationUpdateReq;
import edunhnil.project.forum.api.dto.informationDTO.ListInformationResponse;
import edunhnil.project.forum.api.exception.InvalidRequestException;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.service.AbstractService;
import edunhnil.project.forum.api.ultilities.DateFormat;
import edunhnil.project.forum.api.ultilities.InputValidation;

@Service
public class InformationServiceImpl extends AbstractService<InformationRepository> implements InformationService {

        @Override
        public Optional<ListInformationResponse> getAllInformation(Map<String, String> allParams, String keySort,
                        int page,
                        int pageSize, String sortField) {
                List<Information> information = repository
                                .getAllInformation(allParams, keySort, page, pageSize, sortField)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found information list!"));
                return Optional.of(new ListInformationResponse(information.stream()
                                .map(i -> new InformationResponse(i.getId(), i.getUserId(),
                                                i.getFirstName(), i.getLastName(),
                                                i.getGender(), DateFormat.toDateString(i.getDob(), DateTime.YYYY_MM_DD),
                                                i.getAddress(), i.getEmail(), i.getPhone(),
                                                DateFormat.toDateString(i.getCreated(), DateTime.YYYY_MM_DD),
                                                DateFormat.toDateString(i.getModified(), DateTime.YYYY_MM_DD)))
                                .collect(Collectors.toList()), page, pageSize,
                                repository.getTotalPage(allParams)));
        }

        public void validateRequestParam(InformationRequest informationRequest) {
                InputValidation.validDateFormat(informationRequest.getDob());
                InputValidation.validEmail(informationRequest.getEmail());
                InputValidation.validMobile(informationRequest.getPhone());
                InputValidation.validGender(informationRequest.getGender());
        }

        @Override
        public Optional<InformationResponse> getInformationByUserCode(int id, int deleted) {
                Information information = repository.getInformationByUserCode(id, deleted)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Not found information with user code: " + id));
                return Optional.of(new InformationResponse(information.getId(), information.getUserId(),
                                information.getFirstName(), information.getLastName(),
                                information.getGender(),
                                DateFormat.toDateString(information.getDob(), DateTime.YYYY_MM_DD),
                                information.getAddress(), information.getEmail(), information.getPhone(),
                                DateFormat.toDateString(information.getCreated(), DateTime.YYYY_MM_DD),
                                DateFormat.toDateString(information.getModified(), DateTime.YYYY_MM_DD)));
        }

        @Override
        public Optional<ListInformationResponse> getAllPublicInformation(Map<String, String> allParams, String keySort,
                        int page,
                        int pageSize, String sortField) {
                List<Information> information = repository
                                .getAllInformation(allParams, keySort, page, pageSize, sortField)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found information list!"));
                return Optional.of(new ListInformationResponse(information.stream()
                                .map(i -> new InformationResponse(i.getId(), i.getUserId(),
                                                i.getFirstName(), i.getLastName(), i.getGender(),
                                                DateFormat.toDateString(i.getDob(), DateTime.YYYY_MM_DD), "", "", "",
                                                "", ""))
                                .collect(Collectors.toList()), page, pageSize,
                                repository.getTotalPage(allParams)));
        }

        @Override
        public void addNewInformation(InformationRequest informationRequest) {
                validate(informationRequest);
                validateRequestParam(informationRequest);
                repository.resetId();
                repository.addNewInformation(objectMapper.convertValue(informationRequest, Information.class));
        }

        @Override
        public void updateInformationByUserCode(InformationUpdateReq informationRequest, int id) {
                repository.getInformationByUserCode(id, 0)
                                .orElseThrow(() -> new InvalidRequestException("User code does not exist!"));
                validate(informationRequest);
                validateRequestParam(objectMapper.convertValue(informationRequest, InformationRequest.class));
                repository.updateInformationByUserCode(objectMapper.convertValue(informationRequest, Information.class),
                                id);
        }

        @Override
        public Optional<InformationResponse> getInformationPublic(int id) {
                Information information = repository.getInformationByUserCode(id, 0)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found user code: " + id));
                return Optional.of(new InformationResponse(information.getId(), information.getUserId(),
                                information.getFirstName(),
                                information.getLastName(), information.getGender(),
                                DateFormat.toDateString(information.getDob(), DateTime.YYYY_MM_DD), "", "", "", "",
                                ""));
        }

}
