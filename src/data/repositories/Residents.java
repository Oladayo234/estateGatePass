package data.repositories;

import data.models.Resident;

import java.util.List;

public class Residents implements ResidentRepo {

    @Override
    public List<Resident> findAll() {
        return List.of();
    }

    @Override
    public Resident findById(int id) {
        return null;
    }

    @Override
    public Resident save(Resident resident) {
        return null;
    }

    @Override
    public void delete(Resident resident) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteByObject(Resident resident) {

    }

    @Override
    public void deleteAll() {

    }
}
