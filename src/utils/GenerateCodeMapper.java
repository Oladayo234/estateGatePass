/*
package utils;


import data.models.GatePass;
import dtos.requests.GenerateCodeRequest;
import dtos.requests.GenerateVisitorEntryCodeRequest;
import dtos.responses.GenerateVisitorEntryCodeResponse;

public class GenerateCodeMapper {

    public static GatePass map(GenerateVisitorEntryCodeRequest request){
        GatePass gatePass = new GatePass();
        gatePass.setResidentId(request.getResidentId());
        gatePass.setExpirationDate(request.getExpirationDate());
        gatePass.setVisitorName(request.getVisitorName());
        gatePass.set
        return gatePass;
    }

    public static GenerateVisitorEntryCodeResponse map(GatePass gatePass){
        GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();
        response.setVisitorId(gatePass.getVisitorsId());
        response.setExpirationDate(gatePass.getExpirationDate());
        response.setOtpCode(gatePass.getOtp());
        response.setCodeType(gatePass.getCodeType());
        response.setVisitorName(gatePass.getVisitorName());
        response.setResidentId(gatePass.getResidentId());
        return response;
    }

}
*/
