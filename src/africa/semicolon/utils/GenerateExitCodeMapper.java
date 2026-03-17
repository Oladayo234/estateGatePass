package africa.semicolon.utils;

import africa.semicolon.data.models.GatePass;
import africa.semicolon.data.models.Resident;
import africa.semicolon.dtos.responses.GenerateExitCodeResponse;

public class GenerateExitCodeMapper {

    public static GenerateExitCodeResponse map(GatePass gatePass, Resident resident) {
        GenerateExitCodeResponse response = new GenerateExitCodeResponse();
        if (gatePass.getVisitor() != null) {
            response.setName(gatePass.getVisitor().getName());
            response.setId(gatePass.getVisitor().getId());
            response.setVisitorName(gatePass.getVisitor().getName());
        } else {
            response.setName(resident.getName());
            response.setId(resident.getId());
        }
        response.setExpirationDate(gatePass.getExpirationDate());
        response.setValid(gatePass.isValid());
        response.setOtp(gatePass.getOtp());
        response.setResidentAddress(resident.getHouseAddress());

        return response;
    }
}
