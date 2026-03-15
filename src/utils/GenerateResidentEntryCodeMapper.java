package utils;

import data.models.GatePass;
import data.models.Resident;
import dtos.requests.GenerateResidentEntryCodeRequest;
import dtos.responses.GenerateResidentEntryCodeResponse;

public class GenerateResidentEntryCodeMapper {

    public static GatePass map(GenerateResidentEntryCodeRequest request){
        GatePass gatePass = new GatePass();
        gatePass.setResidentId(request.getResidentId());
        gatePass.setCodeType(request.getCodeType());
        gatePass.setExpirationDate(request.getExpirationDate());
        return gatePass;
    }
    public static GenerateResidentEntryCodeResponse map(GatePass gatePass,  Resident resident) {
        GenerateResidentEntryCodeResponse response = new GenerateResidentEntryCodeResponse();
        response.setResidentId(gatePass.getResidentId());
        response.setCodeType(gatePass.getCodeType());
        response.setExpirationDate(gatePass.getExpirationDate());
        response.setOtpCode(gatePass.getOtp());
        response.setAddress(resident.getHouseAddress());
        response.setName(resident.getName());
        return response;
    }
}
