package africa.semicolon.dtos.responses;

import lombok.Data;

@Data
public class GenerateCodeResponse {
    private String name;
    private String phoneNumber;
    private String purposeOfVisit;
    private String id;

}
