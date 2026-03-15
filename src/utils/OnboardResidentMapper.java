package utils;

import data.models.Resident;
import dtos.requests.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;

public class OnboardResidentMapper {

    public static Resident map(OnboardResidentRequest request) {
        Resident resident = new Resident();
        resident.setName(request.getName());
        resident.setEmail(request.getEmail());
        resident.setHouseAddress(request.getAddress());
        resident.setPhoneNumber(request.getPhoneNumber());
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
