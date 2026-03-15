package utils;

import data.models.GatePass;
import data.models.Visitor;
import dtos.requests.GenerateVisitorEntryCodeRequest;
import dtos.responses.GenerateVisitorEntryCodeResponse;

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
       response.setVisitorName(gatePass.getVisitor().getName());
       response.setVisitorId(gatePass.getVisitor().getId());
       response.setExpirationDate(gatePass.getExpirationDate());
       response.setCodeType(gatePass.getCodeType());
       response.setResidentId(gatePass.getResidentId());
       response.setOtpCode(gatePass.getOtp());
       return response;
    }
}
