package dtos.requests;

import data.models.Type;

public class ValidateCodeRequest {
    private String otp;
    private Type codeType;

    public Type getCodeType() {
        return codeType;
    }

    public void setCodeType(Type codeType) {
        this.codeType = codeType;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
