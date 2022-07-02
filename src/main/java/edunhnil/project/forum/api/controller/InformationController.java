package edunhnil.project.forum.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edunhnil.project.forum.api.dto.informationDTO.InformationRequest;
import edunhnil.project.forum.api.dto.informationDTO.InformationResponse;
import edunhnil.project.forum.api.dto.informationDTO.InformationUpdateReq;
import edunhnil.project.forum.api.dto.informationDTO.ListInformationResponse;
import edunhnil.project.forum.api.service.informationService.InformationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "detail")
@CrossOrigin(origins = "http://localhost:3000/", methods = { RequestMethod.OPTIONS, RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE }, allowedHeaders = "*", allowCredentials = "true")
public class InformationController extends AbstractController<InformationService> {

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "admin/getAllInformation")
    public ResponseEntity<ListInformationResponse> getAllInformation(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String keySort,
            @RequestParam(defaultValue = "modified") String sortField,
            @RequestParam Map<String, String> allParams) {
        return response(service.getAllInformation(allParams, keySort, page, pageSize, sortField));
    }

    @GetMapping(value = "public/getAllInformation")
    public ResponseEntity<ListInformationResponse> getAllPublicInformation(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String keySort, @RequestParam Map<String, String> allParams,
            @RequestParam(defaultValue = "modified") String sortField) {
        return response(service.getAllPublicInformation(allParams, keySort, page, pageSize, sortField));
    }

    @GetMapping(value = "public/getInformation/{id}")
    public ResponseEntity<InformationResponse> getInformationPublic(@PathVariable int id) {
        return response(service.getInformationPublic(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "admin/getInformation/{id}")
    public ResponseEntity<InformationResponse> getInformationAdmin(@RequestParam(defaultValue = "0") int deleted,
            @PathVariable int id) {
        return response(service.getInformationByUserCode(id, deleted));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "user/{id}/getInformation")
    public ResponseEntity<InformationResponse> getInformationUser(@PathVariable int id) {
        return response(service.getInformationByUserCode(id, 0));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "admin/addNewInformation")
    public void addNewInformation(@RequestBody InformationRequest informationRequest) {
        service.addNewInformation(informationRequest);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "admin/updateInformation/{id}")
    public void updateInformationAdmin(@RequestBody InformationUpdateReq informationRequest,
            @PathVariable int id) {
        service.updateInformationByUserCode(informationRequest, id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "user/{id}/updateInformation")
    public void updateInformationUser(@RequestBody InformationUpdateReq informationRequest,
            @PathVariable int id) {
        service.updateInformationByUserCode(informationRequest, id);
    }

}
