package africa.semicolon.services;

import africa.semicolon.data.models.Resident;
import africa.semicolon.data.repositories.ResidentRepo;
import africa.semicolon.dtos.requests.OnboardResidentRequest;
import africa.semicolon.dtos.responses.OnboardResidentResponse;
import africa.semicolon.dtos.responses.ViewResidentResponse;
import africa.semicolon.exceptions.ResidentAlreadyRegisteredException;
import africa.semicolon.exceptions.ResidentDoesNotExistException;
import africa.semicolon.utils.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static africa.semicolon.utils.OnboardResidentMapper.map;
import static africa.semicolon.utils.OnboardResidentMapper.mapToViewResponse;

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
        resident.setId(RandomCodeGenerator.residentIdGenerator());
        Resident savedResident = residentRepo.save(resident);
        return map(savedResident);
    }

    public OnboardResidentResponse viewResident(String id) {
        Resident resident = getResidentById(id);
        return map(resident);
    }

    public List<ViewResidentResponse> viewAllResident() {
        List<ViewResidentResponse> responses = new ArrayList<>();
        for(Resident resident : residentRepo.findAll()) {
            responses.add(mapToViewResponse(resident));
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

    public String reactivateResident(String id) {
        Resident resident = getResidentById(id);
        resident.setSuspended(false);
        residentRepo.save(resident);
        return resident.getName() + " has been reactivated";
    }

    private static void validateResidentDetails(OnboardResidentRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Resident name cannot be blank");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Resident phone cannot be blank");
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!isValidEmail(request.getEmail())) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
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
