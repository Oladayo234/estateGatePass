package africa.semicolon.utils;

import africa.semicolon.data.models.GatePass;
import africa.semicolon.data.models.Visitor;
import africa.semicolon.dtos.requests.GenerateVisitorEntryCodeRequest;
import africa.semicolon.dtos.responses.GenerateVisitorEntryCodeResponse;

public class GenerateEntryVisitorCodeMapper {

    public static GatePass map(GenerateVisitorEntryCodeRequest request, Visitor visitor) {
        GatePass gatePass = new GatePass();
        gatePass.setVisitor(visitor);
        gatePass.setResidentId(request.getResidentId());
        gatePass.setExpirationDate(request.getExpirationDate());
        gatePass.setCodeType(request.getCodeType());
        return gatePass;
    }

    public static GenerateVisitorEntryCodeResponse map(GatePass gatePass) {
       GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();
       response.setGatePassId(gatePass.getId());
       response.setVisitorName(gatePass.getVisitor().getName());
       response.setVisitorId(gatePass.getVisitor().getId());
       response.setExpirationDate(gatePass.getExpirationDate());
       response.setCodeType(gatePass.getCodeType());
       response.setResidentId(gatePass.getResidentId());
       response.setOtpCode(gatePass.getOtp());
       return response;
    }
}
