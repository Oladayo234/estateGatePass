package africa.semicolon.services;

import africa.semicolon.data.models.Resident;
import africa.semicolon.data.repositories.ResidentRepo;
import africa.semicolon.dtos.requests.OnboardResidentRequest;
import africa.semicolon.dtos.responses.OnboardResidentResponse;
import africa.semicolon.exceptions.ResidentAlreadyRegisteredException;
import africa.semicolon.exceptions.ResidentDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static africa.semicolon.utils.OnboardResidentMapper.map;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidentManagementService {
    private final ResidentRepo residentRepo;

    public OnboardResidentResponse onboardResident(OnboardResidentRequest request) {
        validateResidentDetails(request);
        Resident resident = map(request);
        validateCheckDuplicateFor(resident);
        Resident savedResident = residentRepo.save(resident);
        return map(savedResident);
    }

    public OnboardResidentResponse viewResident(String id) {
        Resident resident = getResidentById(id);
        return map(resident);
    }

    public List<OnboardResidentResponse> viewAllResident() {
        List<OnboardResidentResponse> responses = new ArrayList<>();
        for(Resident resident : residentRepo.findAll()) {
            responses.add(map(resident));
        }
        return responses;
    }

    public void deleteResident(String id) {
        Resident resident = getResidentById(id);
        residentRepo.delete(resident);
    }

    public String disableResident(String id) {
        Resident resident = getResidentById(id);
        resident.setSuspended(true);
        residentRepo.save(resident);
        return resident.getName() + " has been suspended";
    }

    private static void validateResidentDetails(OnboardResidentRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Resident name cannot be blank");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Resident phone cannot be blank");
        }
    }

    private Resident getResidentById(String id) {
        return residentRepo.findById(id).orElseThrow(() ->
                new ResidentDoesNotExistException("Resident not found"));
    }

    private void validateCheckDuplicateFor(Resident resident) {
        if (residentRepo.findByPhoneNumber(resident.getPhoneNumber()) != null)
            throw new ResidentAlreadyRegisteredException("Resident already registered");
    }


}
