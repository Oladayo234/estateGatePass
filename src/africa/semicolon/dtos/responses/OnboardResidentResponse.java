package africa.semicolon.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OnboardResidentResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String id;
    private LocalDateTime dateOfRegistration = LocalDateTime.now();
    private boolean isSuspended;



}
