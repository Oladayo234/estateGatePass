package africa.semicolon.dtos.responses;

import lombok.Data;

@Data
public class ViewResidentResponse {
    private String name;
    private String phoneNumber;
    private String houseAddress;
    private String id;

}
