package africa.semicolon.dtos.requests;

import africa.semicolon.data.models.Type;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GenerateCodeRequest {
    private String residentId;
    private String visitorId;
    private LocalDateTime expirationDate;
    private Type type;

}
