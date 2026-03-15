package data.repositories;
import data.models.Visitor;
import java.util.List;

public interface VisitorRepo {
    List<Visitor> findAll();
    Visitor findById(String Id);
    Visitor findByName(String name);
    Visitor findByPhone(String phone);
    Visitor save(Visitor visitor);
    void delete(Visitor visitor);
    void deleteById(String Id);
    void deleteAll();
    long count();
}