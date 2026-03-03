package data.repositories;

import data.models.GatePass;
import java.util.List;

public interface GatePassRepo {
    List<GatePass> findAll();
    GatePass findById(int id);
    void save(GatePass pass);
    void delete(GatePass pass);
    void deleteById(int id);
    void deleteAll();
    long count();
}