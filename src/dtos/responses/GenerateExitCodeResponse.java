package dtos.responses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateExitCodeResponse {
    private String id;
    private LocalDateTime expirationDate;
    private String otp;
    private String residentAddress;
    private String visitorName;
    private String name;
    private boolean isValid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getResidentAddress() {
        return residentAddress;
    }

    public void setResidentAddress(String residentAddress) {
        this.residentAddress = residentAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getFormattedExpirationDate() {
        if (expirationDate == null) return null;
        return expirationDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a"));
    }
}
