package data.repositories;

import java.util.List;

public class GatePasses implements GatePassRepo{
    @Override
    public List<GatePasses> findAll() {
        return List.of();
    }

    @Override
    public GatePasses findById(int Id) {
        return null;
    }

    @Override
    public GatePasses save(GatePasses pass) {
        return null;
    }

    @Override
    public void delete(GatePasses pass) {

    }

    @Override
    public void deleteById(int Id) {

    }

    @Override
    public void deleteByObject(GatePasses pass) {

    }

    @Override
    public void deleteAll() {

    }
}
