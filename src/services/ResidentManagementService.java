package services;

import data.models.Resident;
import data.repositories.ResidentRepo;
import data.repositories.Residents;
import dtos.requests.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentAlreadyRegisteredException;
import exceptions.ResidentDoesNotExistException;
import utils.OnboardResidentMapper;

public class ResidentManagementService {
    private ResidentRepo residentRepo = new Residents();

    OnboardResidentResponse onboardResident(OnboardResidentRequest request) {
        Resident resident = OnboardResidentMapper.map(request);
        if (residentRepo.findByPhone(resident.getPhoneNumber()) != null)
            throw new ResidentAlreadyRegisteredException("Resident already registered");
        Resident savedResident = residentRepo.save(resident);
        return OnboardResidentMapper.map(savedResident);
    }

    OnboardResidentResponse viewResident(String id) {
        Resident resident = getResidentById(id);
        return OnboardResidentMapper.map(resident);
    }

    void deleteResident(String id) {
        Resident resident = getResidentById(id);
        residentRepo.delete(resident);
    }

    OnboardResidentResponse disableResident(String id) {
        Resident resident = getResidentById(id);
        resident.setSuspended(true);
        residentRepo.save(resident);
        return OnboardResidentMapper.map(resident);
    }

    private Resident getResidentById(String id) {
        Resident resident = residentRepo.findById(id);
        if (resident == null) throw new ResidentDoesNotExistException("Resident not found");
        return resident;
    }


}
