package africa.semicolon.dtos.requests;

import africa.semicolon.data.models.Type;
import lombok.Data;

@Data
public class ValidateCodeRequest {
    private String otp;
    private Type codeType;

}
