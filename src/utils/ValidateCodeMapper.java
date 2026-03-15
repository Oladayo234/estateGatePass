package utils;

import data.models.GatePass;
import data.models.Resident;
import dtos.requests.ValidateCodeRequest;
import dtos.responses.ValidateCodeResponse;

public class ValidateCodeMapper {

    public static GatePass map(ValidateCodeRequest request) {
        GatePass gatePass = new GatePass();
        gatePass.setCodeType(request.getCodeType());
        gatePass.setOtp(request.getOtp());
        return gatePass;
    }

    public static ValidateCodeResponse map(GatePass gatePass, Resident resident) {
        ValidateCodeResponse response = new ValidateCodeResponse();
        response.setAddress(resident.getHouseAddress());
        response.setResidentName(resident.getName());
        response.setCodeType(gatePass.getCodeType());
        response.setValid(gatePass.isValid());

        if (gatePass.getVisitor() != null) {
            response.setVisitorName(gatePass.getVisitor().getName());
        }
        return response;
    }
}
