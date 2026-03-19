package africa.semicolon.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class GenerateExitCodeResponse {
    private String id;

    @JsonFormat(pattern = "dd-MMM-yyyy hh:mm a")
    private LocalDateTime expirationDate;
    private String otp;
    private String residentAddress;
    private String visitorName;
    private String name;
    private boolean isValid;

    public String getFormattedExpirationDate() {
        if (expirationDate == null) return null;
        return expirationDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a"));
    }
}
