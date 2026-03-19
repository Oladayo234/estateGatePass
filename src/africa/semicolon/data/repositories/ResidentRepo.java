package africa.semicolon.data.repositories;
import africa.semicolon.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResidentRepo extends MongoRepository<Resident,String> {
    Resident findByPhoneNumber(String phoneNumber);
}