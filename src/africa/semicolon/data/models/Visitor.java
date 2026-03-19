package africa.semicolon.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "visitors")
public class Visitor {
    @Id
    private String id;
    private String name;
    private String purposeOfVisit;
    private String phoneNumber;

}
