package africa.semicolon.data.repositories;
import africa.semicolon.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResidentRepo extends MongoRepository<Resident,String> {
    Resident findByPhoneNumber(String phoneNumber);
}