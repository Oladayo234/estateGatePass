package data.repositories;

import java.util.List;

public interface GatePassRepo {

    List<GatePasses> findAll();
    GatePasses findById(int Id);
    GatePasses save(GatePasses pass);
    void delete(GatePasses pass);
    void deleteById(int Id);
    void deleteByObject(GatePasses pass);
    void deleteAll();



}
