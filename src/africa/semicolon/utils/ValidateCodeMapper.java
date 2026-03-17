package africa.semicolon.utils;

import africa.semicolon.data.models.GatePass;
import africa.semicolon.data.models.Resident;
import africa.semicolon.dtos.requests.ValidateCodeRequest;
import africa.semicolon.dtos.responses.ValidateCodeResponse;

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
