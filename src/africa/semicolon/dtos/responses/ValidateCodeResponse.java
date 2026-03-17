package africa.semicolon.dtos.responses;

import africa.semicolon.data.models.Type;
import lombok.Data;

@Data
public class ValidateCodeResponse {
    private String residentName;
    private String visitorName;
    private Type codeType;
    private String address;
    private boolean isValid;

}

