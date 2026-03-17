package africa.semicolon.dtos.responses;

import africa.semicolon.data.models.Type;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class GenerateVisitorEntryCodeResponse {
    private String residentId;
    private String visitorName;
    private String visitorId;
    private String otpCode;
    private Type codeType;
    private LocalDateTime expirationDate;

    public String getFormattedExpirationDate() {
        if (expirationDate == null) return null;
        return expirationDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a"));
    }
}
