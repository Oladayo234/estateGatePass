package services;

import data.models.Resident;
import data.repositories.ResidentRepo;
import data.repositories.Residents;
import dtos.requests.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentAlreadyRegisteredException;
import exceptions.ResidentDoesNotExistException;
import static utils.OnboardResidentMapper.map;
import java.util.ArrayList;
import java.util.List;


public class ResidentManagementService {
    private ResidentRepo residentRepo = new Residents();

    OnboardResidentResponse onboardResident(OnboardResidentRequest request) {
        Resident resident = map(request);
        validateCheckDuplicateFor(resident);
        Resident savedResident = residentRepo.save(resident);
        return map(savedResident);
    }

    OnboardResidentResponse viewResident(String id) {
        Resident resident = getResidentById(id);
        return map(resident);
    }

    public List<OnboardResidentResponse> viewResident() {
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
        return resident.getName() + "Resident has been suspended";
    }

    private Resident getResidentById(String id) {
        Resident resident = residentRepo.findById(id);
        if (resident == null) throw new ResidentDoesNotExistException("Resident not found");
        return resident;
    }

    private void validateCheckDuplicateFor(Resident resident) {
        if (residentRepo.findByPhone(resident.getPhoneNumber()) != null)
            throw new ResidentAlreadyRegisteredException("Resident already registered");
    }


}
