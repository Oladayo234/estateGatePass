package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class OnboardResidentRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

}
