package africa.semicolon.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OnboardResidentResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String id;

    @JsonFormat(pattern = "dd-MMM-yyyy hh:mm a")
    private LocalDateTime dateOfRegistration = LocalDateTime.now();
    private boolean isSuspended;



}
