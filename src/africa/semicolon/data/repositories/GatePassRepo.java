package africa.semicolon.data.repositories;

import africa.semicolon.data.models.GatePass;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface GatePassRepo extends MongoRepository<GatePass, String> {
    GatePass findByOtp(String gatePassId);
}