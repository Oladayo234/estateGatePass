package africa.semicolon.dtos.requests;

import africa.semicolon.data.models.Type;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenerateVisitorEntryCodeRequest {
    private String visitorName;
    private String visitorPhoneNumber;
    private String purposeOfVisit;
    private String residentId;
    private Type codeType;
    private LocalDateTime expirationDate;

}
