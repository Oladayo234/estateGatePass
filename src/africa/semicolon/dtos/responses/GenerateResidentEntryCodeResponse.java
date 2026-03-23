package africa.semicolon.dtos.responses;

import africa.semicolon.data.models.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
public class GenerateResidentEntryCodeResponse {
    private String residentId;
    private String otpCode;
    private Type codeType;
    private String gatePassId;

    @JsonIgnore
    @JsonFormat(pattern = "dd-MMM-yyyy hh:mm a")
    private LocalDateTime expirationDate;
    private String name;
    private String address;

    public String getFormattedExpirationDate() {
        if (expirationDate == null) return null;
        return expirationDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a"));
    }
}
