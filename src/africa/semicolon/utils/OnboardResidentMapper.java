package africa.semicolon.utils;

import africa.semicolon.data.models.Resident;
import africa.semicolon.dtos.requests.OnboardResidentRequest;
import africa.semicolon.dtos.responses.OnboardResidentResponse;

import java.time.LocalDateTime;

public class OnboardResidentMapper {

    public static Resident map(OnboardResidentRequest request) {
        Resident resident = new Resident();
        resident.setName(request.getName());
        resident.setEmail(request.getEmail());
        resident.setHouseAddress(request.getAddress());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setDateOfRegistration(LocalDateTime.now());
        return resident;
    }

    public static OnboardResidentResponse map(Resident resident) {
        OnboardResidentResponse response = new OnboardResidentResponse();
        response.setId(resident.getId());
        response.setName(resident.getName());
        response.setEmail(resident.getEmail());
        response.setAddress(resident.getHouseAddress());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setDateOfRegistration(resident.getDateOfRegistration());
        response.setSuspended(resident.isSuspended());
        return response;
    }
}
